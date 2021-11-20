package sotd.spotify.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimplifedAlbumObject {
    private final String albumGroup;
    private final String albumType;
    private final List<SimplifiedArtistObject> artists;
    private final String href;
    private final String id;
    private final String name;
    private final String releaseDate;
    private final String type;
    private final String uri;

    @JsonCreator
    public SimplifedAlbumObject(@JsonProperty("album_group") String albumGroup,
                                @JsonProperty("album_type") String albumType,
                                @JsonProperty("artists") List<SimplifiedArtistObject> artists,
                                @JsonProperty("href") String href,
                                @JsonProperty("id") String id,
                                @JsonProperty("name") String name,
                                @JsonProperty("release_date") String releaseDate,
                                @JsonProperty("type") String type,
                                @JsonProperty("uri") String uri) {
        this.albumGroup = albumGroup;
        this.albumType = albumType;
        this.artists = artists;
        this.href = href;
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.type = type;
        this.uri = uri;
    }

    public String getAlbumGroup() {
        return albumGroup;
    }

    public String getAlbumType() {
        return albumType;
    }

    public List<SimplifiedArtistObject> getArtists() {
        return artists;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }
}
