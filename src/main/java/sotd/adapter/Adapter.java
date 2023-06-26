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
        // Compare lists and update Notion
        return getModifications(songs, existingSongs);
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
        return new Modification(toBeAdded, toBeRemoved);
    }

    private List<Song> getSongs(PlaylistObject playlist) {
        return playlist.tracks().items().stream().map(Song::fromTrack).collect(Collectors.toList());
    }

    public record Modification(Set<Song> toBeAdded, Set<Song> toBeRemoved) {}
}
