package sotd;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sotd.adapter.Adapter;
import sotd.common.model.Song;
import sotd.notion.NotionService;
import sotd.notion.model.Page;
import sotd.notion.model.PropertyValue;
import sotd.spotify.Spotify;
import sotd.spotify.SpotifyProperties;
import sotd.spotify.model.PlaylistObject;
import sotd.spotify.model.TrackObject;

@Component
public class Executor {

    private static final Logger logger = LoggerFactory.getLogger(Executor.class);

    private final NotionService notionService;

    private final Spotify spotify;

    private final SpotifyProperties spotifyProperties;

    @Autowired
    public Executor(NotionService notionService, Spotify spotify, SpotifyProperties spotifyProperties) {
        this.notionService = notionService;
        this.spotify = spotify;
        this.spotifyProperties = spotifyProperties;
    }

    public void execute() {
        PlaylistObject playlist = spotify.getPlaylist(spotifyProperties.getPlaylistId());
        logger.info("{}: {}", playlist.getName(), playlist.getDescription());

        List<Page> entries = notionService.getTracks();
        List<Song> existingSongs = entries.stream()
                .map(p -> {
                    Map<String, PropertyValue> properties = p.getProperties();
                    return new Song(
                            properties.get("Title").getId(),
                            List.of(properties.get("Artists").getId().split(", ")));
                })
                .collect(Collectors.toList());
        Adapter adapter = new Adapter();
        Adapter.Modification newSongs = adapter.findNewSongs(playlist, existingSongs);

        List<TrackObject> newTracks = playlist.getTracks().getItems().stream()
                .filter(t -> {
                    Song song = Song.fromTrack(t);
                    return newSongs.toBeAdded.contains(song);
                })
                .collect(Collectors.toList());

        newTracks.forEach(notionService::addTrack);
    }
}
