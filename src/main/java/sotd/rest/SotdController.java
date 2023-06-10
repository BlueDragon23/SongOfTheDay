package sotd.rest;

import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import sotd.Executor;
import sotd.spotify.SpotifyProperties;
import sotd.spotify.model.AccessTokenRequest;
import sotd.spotify.model.AccessTokenResponse;
import sotd.spotify.oauth.SpotifyOAuth;

@RestController
public class SotdController {

    public static final String REDIRECT_URI = "http://localhost:8080/callback";
    private static final Logger logger = LoggerFactory.getLogger(SotdController.class);
    private static final JsonMapper JSON_MAPPER = JsonMapper.builder().build();

    private final Executor executor;
    private final SpotifyOAuth spotifyOAuth;
    private final SpotifyProperties spotifyProperties;

    @Autowired
    public SotdController(Executor executor, SpotifyOAuth spotifyOAuth, SpotifyProperties spotifyProperties) {
        this.executor = executor;
        this.spotifyOAuth = spotifyOAuth;
        this.spotifyProperties = spotifyProperties;
    }

    @GetMapping("/execute")
    public void execute() {
        executor.execute();
    }

    @GetMapping("/login")
    public ResponseEntity<?> login() {
        // Return redirect to Spotify auth
        HttpHeaders headers = new HttpHeaders();
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(spotifyProperties.getAccountUrl())
                .path("/authorize")
                .queryParam("client_id", spotifyProperties.getClientId())
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("state", "test")
                .queryParam("scope", "playlist-read-private")
                .build();
        headers.setLocation(uriComponents.toUri());
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/callback")
    public void callback(String code, String state) {
        if (!state.equals("test")) {
            logger.error("Received invalid state value={}", state);
            return;
        }
        String auth = getAuthHeader();
        AccessTokenResponse accessTokenResponse = spotifyOAuth.token(new AccessTokenRequest(code), auth);
        try {
            JSON_MAPPER.writeValue(new File("access_token"), accessTokenResponse);
        } catch (IOException e) {
            logger.error("Failed to write access token file", e);
        }
    }

    /**
     * @return base64 encoded client_id:client_secret
     */
    private String getAuthHeader() {
        String header = String.format("%s:%s", spotifyProperties.getClientId(), spotifyProperties.getClientSecret());
        return Base64.getEncoder().encodeToString(header.getBytes(StandardCharsets.UTF_8));
    }
}
