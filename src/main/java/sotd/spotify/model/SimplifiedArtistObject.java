package sotd.spotify.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimplifiedArtistObject {
    private final String href;
    private final String id;
    private final String name;
    private final String type;
    private final String uri;

    @JsonCreator
    public SimplifiedArtistObject(@JsonProperty("href") String href,
                                  @JsonProperty("id") String id,
                                  @JsonProperty("name") String name,
                                  @JsonProperty("type") String type,
                                  @JsonProperty("uri") String uri) {
        this.href = href;
        this.id = id;
        this.name = name;
        this.type = type;
        this.uri = uri;
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

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }
}
