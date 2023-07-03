package sotd;

import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sotd.adapter.Adapter;
import sotd.common.model.Song;
import sotd.notion.NotionService;
import sotd.notion.model.Page;
import sotd.spotify.Spotify;
import sotd.spotify.SpotifyProperties;
import sotd.spotify.model.PlaylistObject;

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
        logger.info("There are {} existing entries", entries.size());

        List<Song> existingSongs = notionService.convertTracks(entries);
        List<Song> allSongs = Adapter.getSongs(playlist);
        Set<Song> newSongs = Adapter.getNewSongs(allSongs, existingSongs);

        newSongs.forEach(notionService::addTrack);
        logger.info("Completed updating Notion");
    }
}
