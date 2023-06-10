package sotd.adapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import sotd.common.model.Song;
import sotd.spotify.model.PlaylistObject;

public class Adapter {

    public Modification findNewSongs(final PlaylistObject playlist, final List<Song> existingSongs) {
        final List<Song> songs = getSongs(playlist);
        // Compare lists
        final Modification modification = getModifications(songs, existingSongs);
        // Update Notion
        return modification;
    }

    private Modification getModifications(List<Song> ids, List<Song> existingIds) {
        final Set<Song> idSet = new HashSet<>(ids);
        final Set<Song> existingIdSet = new HashSet<>(existingIds);
        final Set<Song> toBeAdded = new HashSet<>();
        for (Song id : ids) {
            if (!existingIdSet.contains(id)) {
                toBeAdded.add(id);
            }
        }
        final Set<Song> toBeRemoved = new HashSet<>();
        for (Song id : existingIds) {
            if (!idSet.contains(id)) {
                toBeRemoved.add(id);
            }
        }
        final Modification modification = new Modification();
        modification.toBeAdded = toBeAdded;
        modification.toBeRemoved = toBeRemoved;
        return modification;
    }

    private List<Song> getSongs(PlaylistObject playlist) {
        return playlist.getTracks().getItems().stream().map(Song::fromTrack).collect(Collectors.toList());
    }

    public static class Modification {
        public Set<Song> toBeAdded;
        public Set<Song> toBeRemoved;
    }
}
