package sotd.spotify.oauth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sotd.spotify.SpotifyProperties;

@Component
public class OAuthInterceptor implements RequestInterceptor {

    private final SpotifyProperties spotifyProperties;

    @Autowired
    public OAuthInterceptor(SpotifyProperties spotifyProperties) {
        this.spotifyProperties = spotifyProperties;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header(
                "Authorization",
                "Bearer " + spotifyProperties.getAccessTokenResponse().accessToken());
    }
}
