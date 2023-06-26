package sotd.spotify.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@JsonAutoDetect
public record PlaylistTrackObject(String addedAt, TrackObject track) {
    @JsonCreator
    public PlaylistTrackObject(@JsonProperty("added_at") String addedAt, @JsonProperty("track") TrackObject track) {
        this.addedAt = addedAt;
        this.track = track;
    }

    @JsonIgnore
    public LocalDate getAddedAt() {
        return LocalDate.parse(addedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
