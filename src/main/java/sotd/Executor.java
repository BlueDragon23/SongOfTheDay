package sotd;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import sotd.adapter.Adapter;
import sotd.common.model.Song;
import sotd.notion.NotionService;
import sotd.notion.model.Page;
import sotd.notion.model.PropertyValue;
import sotd.spotify.Spotify;
import sotd.spotify.model.PlaylistObject;
import sotd.spotify.model.TrackObject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Executor {

    private final String spotifyToken;
    private final String spotifyPlaylistId;
    private final NotionService notionService;

    @Inject
    public Executor(@Named("spotify.api.token") String spotifyToken, @Named("spotify.playlist.id") String spotifyPlaylistId, NotionService notionService) {
        this.spotifyToken = spotifyToken;
        this.spotifyPlaylistId = spotifyPlaylistId;
        this.notionService = notionService;
    }

    public void execute() {

        // TODO: make oauth work
        Spotify spotify = Feign.builder()
                .requestInterceptor(input -> input.header("Authorization", spotifyToken))
                .logger(new Logger.JavaLogger("Spotify.Logger").appendToFile("logs/sotd.spotify.log"))
                .logLevel(Logger.Level.BASIC)
                .encoder(new JacksonEncoder(List.of(new Jdk8Module())))
                .decoder(new JacksonDecoder(List.of(new Jdk8Module())))
                .target(Spotify.class, "https://api.spotify.com");

        PlaylistObject playlist = spotify.getPlaylist(spotifyPlaylistId);
        System.out.printf("%s: %s%n", playlist.getName(), playlist.getDescription());

        List<Page> entries = notionService.getTracks();
        List<Song> existingSongs = entries.stream().map(p -> {
            Map<String, PropertyValue> properties = p.getProperties();
            return new Song(properties.get("Title").getId(), List.of(properties.get("Artists").getId().split(", ")));
        }).collect(Collectors.toList());
        Adapter adapter = new Adapter();
        Adapter.Modification newSongs = adapter.findNewSongs(playlist, existingSongs);

        List<TrackObject> newTracks = playlist.getTracks().getItems().stream().filter(t -> {
            Song song = Song.fromTrack(t);
            return newSongs.toBeAdded.contains(song);
        }).collect(Collectors.toList());

        newTracks.forEach(notionService::addTrack);
    }
}
