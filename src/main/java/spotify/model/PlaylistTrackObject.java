package spotify.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaylistTrackObject {
    private final LocalDateTime addedAt;
    private final String addedBy;
    private final boolean isLocal;
    private final TrackObject track;

    @JsonCreator
    public PlaylistTrackObject(@JsonProperty("added_at") LocalDateTime addedAt,
                               @JsonProperty("added_by") String addedBy,
                               @JsonProperty("is_local") boolean isLocal,
                               @JsonProperty("track") TrackObject track) {
        this.addedAt = addedAt;
        this.addedBy = addedBy;
        this.isLocal = isLocal;
        this.track = track;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public TrackObject getTrack() {
        return track;
    }
}
