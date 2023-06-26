package sotd.spotify.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonAutoDetect
public record TrackObject(
        SimplifedAlbumObject album,
        List<ArtistObject> artists,
        int durationMs,
        String href,
        String id,
        boolean isLocal,
        String name,
        String previewUrl,
        int trackNumber,
        String type,
        String uri) {
    public TrackObject(
            @JsonProperty("album") SimplifedAlbumObject album,
            @JsonProperty("artists") List<ArtistObject> artists,
            @JsonProperty("duration_ms") int durationMs,
            @JsonProperty("href") String href,
            @JsonProperty("id") String id,
            @JsonProperty("is_local") boolean isLocal,
            @JsonProperty("name") String name,
            @JsonProperty("preview_url") String previewUrl,
            @JsonProperty("track_number") int trackNumber,
            @JsonProperty("type") String type,
            @JsonProperty("uri") String uri) {
        this.album = album;
        this.artists = artists;
        this.durationMs = durationMs;
        this.href = href;
        this.id = id;
        this.isLocal = isLocal;
        this.name = name;
        this.previewUrl = previewUrl;
        this.trackNumber = trackNumber;
        this.type = type;
        this.uri = uri;
    }
}
