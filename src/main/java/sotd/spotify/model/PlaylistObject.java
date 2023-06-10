package sotd.spotify.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaylistObject {
    private final boolean collaborative;
    private final String description;
    private final String href;
    private final String id;
    private final String name;
    private final String snapshotId;
    private final PlaylistTracks tracks;
    private final String type;
    private final String uri;

    @JsonCreator
    public PlaylistObject(
            @JsonProperty("collaborative") boolean collaborative,
            @JsonProperty("description") String description,
            @JsonProperty("href") String href,
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("snapshot_id") String snapshotId,
            @JsonProperty("tracks") PlaylistTracks tracks,
            @JsonProperty("type") String type,
            @JsonProperty("uri") String uri) {
        this.collaborative = collaborative;
        this.description = description;
        this.href = href;
        this.id = id;
        this.name = name;
        this.snapshotId = snapshotId;
        this.tracks = tracks;
        this.type = type;
        this.uri = uri;
    }

    public boolean isCollaborative() {
        return collaborative;
    }

    public String getDescription() {
        return description;
    }

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public PlaylistTracks getTracks() {
        return tracks;
    }

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }

    @JsonAutoDetect
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PlaylistTracks {
        private final String href;
        private final List<TrackObject> items;

        @JsonCreator
        private PlaylistTracks(@JsonProperty("href") String href, @JsonProperty("items") List<TrackObject> items) {
            this.href = href;
            this.items = items;
        }

        public String getHref() {
            return href;
        }

        public List<TrackObject> getItems() {
            return items;
        }
    }
}
