package sotd.spotify.oauth;

import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OAuthFlow {

    public void doOAuth() {
        SpotifyOAuth oauthClient = Feign.builder()
                .logger(new Logger.JavaLogger("Spotify.Logger").appendToFile("logs/spotifyOAuth.log"))
                .logLevel(Logger.Level.NONE)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(SpotifyOAuth.class, "https://accounts.spotify.com");

        Map<String, String> authorizationParameters = new HashMap<>();
        authorizationParameters.put("client_id", Constants.CLIENT_ID);
        authorizationParameters.put("response_type", "code");
        authorizationParameters.put("redirect_uri", "?");
        UUID state = UUID.randomUUID();
        authorizationParameters.put("state", state.toString());
        authorizationParameters.put("scope", "playlist-read-private");
        authorizationParameters.put("show_dialog", Boolean.FALSE.toString());
        oauthClient.authorize(authorizationParameters);
        // Will receive a callback

    }
}
