package sotd.spotify;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import sotd.spotify.model.AccessTokenResponse;

@Configuration
@ConfigurationProperties(prefix = "spotify")
public class SpotifyProperties {

    private String clientId;

    private String clientSecret;

    private String playlistId;

    private String accountUrl;

    private AccessTokenResponse accessTokenResponse;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getAccountUrl() {
        return accountUrl;
    }

    public void setAccountUrl(String accountUrl) {
        this.accountUrl = accountUrl;
    }

    public AccessTokenResponse getAccessTokenResponse() {
        return accessTokenResponse;
    }

    public void setAccessTokenResponse(AccessTokenResponse accessTokenResponse) {
        this.accessTokenResponse = accessTokenResponse;
    }
}
