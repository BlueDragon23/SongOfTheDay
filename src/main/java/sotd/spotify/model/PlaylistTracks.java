package sotd.spotify.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.List;

@JsonAutoDetect
public record PlaylistTracks(String href, List<PlaylistTrackObject> items) {}
