package sotd.spotify.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public record PlaylistObject(
        boolean collaborative,
        String description,
        String href,
        String id,
        String name,
        String snapshotId,
        PlaylistTracks tracks,
        String type,
        String uri) {
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
}
