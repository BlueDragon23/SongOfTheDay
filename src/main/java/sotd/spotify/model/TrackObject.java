package sotd.spotify.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackObject {
    private final SimplifedAlbumObject album;
    private final List<ArtistObject> artists;
    private final int durationMs;
    private final String href;
    private final String id;
    private final boolean isLocal;
    private final String name;
    private final String previewUrl;
    private final int trackNumber;
    private final String type;

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

    public SimplifedAlbumObject getAlbum() {
        return album;
    }

    public List<ArtistObject> getArtists() {
        return artists;
    }

    public int getDurationMs() {
        return durationMs;
    }

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public String getName() {
        return name;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }

    private final String uri;
}
