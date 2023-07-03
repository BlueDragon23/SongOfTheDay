package sotd.adapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import sotd.common.model.Song;
import sotd.spotify.model.PlaylistObject;

public final class Adapter {

    private Adapter() {
        // hm
    }

    public static Set<Song> getNewSongs(List<Song> allSongs, List<Song> existingSongs) {
        Set<Song> result = new HashSet<>(allSongs);
        existingSongs.forEach(result::remove);
        return result;
    }

    public static List<Song> getSongs(PlaylistObject playlist) {
        return playlist.tracks().items().stream().map(Song::fromTrack).collect(Collectors.toList());
    }
}
