package sotd.spotify;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spotify")
public class SpotifyProperties {

    private static final String CLIENT_ID = "5442acc3a29d44eca84a0d6b68dd9cf7";

    private String clientSecret;

    private String playlistId;

    public String getClientId() {
        return CLIENT_ID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getPlaylistId() {
        return playlistId;
    }
}
