package sotd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sotd.adapter.Adapter;
import sotd.common.model.Song;
import sotd.notion.NotionService;
import sotd.notion.model.Page;
import sotd.notion.model.propertyvalues.DatePropertyValue;
import sotd.notion.model.propertyvalues.RichTextPropertyValue;
import sotd.notion.model.propertyvalues.TitlePropertyValue;
import sotd.spotify.Spotify;
import sotd.spotify.SpotifyProperties;
import sotd.spotify.model.PlaylistObject;
import sotd.spotify.model.PlaylistTrackObject;

import java.util.List;
import java.util.stream.Collectors;

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
        logger.info("{}: {}", playlist.name(), playlist.description());

        List<Page> entries = notionService.getTracks();
        logger.info("There are {} entries", entries.size());
        if (entries.size() > 0) {
            logger.info("First entry is {}", entries.get(0));
        }
        List<Song> existingSongs = entries.stream()
                .map(p -> {
                    TitlePropertyValue title = p.getPropertyValue("Title");
                    DatePropertyValue dateAdded = p.getPropertyValue("Date Added");
                    RichTextPropertyValue artists = p.getPropertyValue("Artists");
                    return new Song(
                            title.getPlainText(),
                            List.of(artists.getPlainText().split(", ")),
                            dateAdded.getDateObject().getStartDate());
                })
                .collect(Collectors.toList());
        Adapter adapter = new Adapter();
        Adapter.Modification newSongs = adapter.findNewSongs(playlist, existingSongs);

        List<PlaylistTrackObject> newTracks = playlist.tracks().items().stream()
                .filter(t -> {
                    Song song = Song.fromTrack(t);
                    return newSongs.toBeAdded().contains(song);
                })
                .toList();

        newTracks.forEach(notionService::addTrack);
        logger.info("Completed updating Notion");
    }
}
