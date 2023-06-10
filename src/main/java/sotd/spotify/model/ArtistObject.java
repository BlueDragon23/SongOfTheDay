package sotd.spotify.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtistObject extends SimplifiedArtistObject {
    private final List<String> genres;

    @JsonCreator
    public ArtistObject(
            @JsonProperty("href") String href,
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("type") String type,
            @JsonProperty("uri") String uri,
            @JsonProperty("genres") List<String> genres) {
        super(href, id, name, type, uri);
        this.genres = genres;
    }

    public List<String> getGenres() {
        return genres;
    }
}
