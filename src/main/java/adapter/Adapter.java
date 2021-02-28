package adapter;

import spotify.model.PlaylistObject;
import spotify.model.TrackObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Adapter {

    public void findNewSongs(final PlaylistObject playlist) {
        // Get current state from Notion/other provider
        final List<String> existingIds = Collections.emptyList();
        final List<String> ids = getTrackIds(playlist);
        // Compare lists
        final Modification modification = getModifications(ids, existingIds);
        // Update Notion
    }

    private Modification getModifications(List<String> ids, List<String> existingIds) {
        final Set<String> idSet = new HashSet<>(ids);
        final Set<String> existingIdSet = new HashSet<>(existingIds);
        final List<String> toBeAdded = new ArrayList<>();
        for (String id : ids) {
            if (!existingIdSet.contains(id)) {
                toBeAdded.add(id);
            }
        }
        final List<String> toBeRemoved = new ArrayList<>();
        for (String id : existingIds) {
            if (!idSet.contains(id)) {
                toBeRemoved.add(id);
            }
        }
        final Modification modification = new Modification();
        modification.toBeAdded = toBeAdded;
        modification.toBeRemoved = toBeRemoved;
        return modification;
    }

    private List<String> getTrackIds(PlaylistObject playlist) {
        return playlist
                .getTracks()
                .getItems()
                .stream()
                .map(TrackObject::getId)
                .collect(Collectors.toList());
    }

    static class Modification {
        public List<String> toBeAdded;
        public List<String> toBeRemoved;
    }
}
