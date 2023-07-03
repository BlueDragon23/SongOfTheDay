package sotd.rest;

import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
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
import sotd.common.model.Song;
import sotd.notion.NotionService;
import sotd.spotify.Spotify;
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
    private final Spotify spotify;
    private final NotionService notionService;

    @Autowired
    public SotdController(
            Executor executor,
            SpotifyOAuth spotifyOAuth,
            SpotifyProperties spotifyProperties,
            Spotify spotify,
            NotionService notionService) {
        this.executor = executor;
        this.spotifyOAuth = spotifyOAuth;
        this.spotifyProperties = spotifyProperties;
        this.spotify = spotify;
        this.notionService = notionService;
    }

    @GetMapping("/execute")
    public void execute() {
        executor.execute();
    }

    // Spotify test

    @GetMapping("/fetch-playlist")
    public ResponseEntity<?> fetchPlaylist() {
        return new ResponseEntity<>(spotify.getPlaylist(spotifyProperties.getPlaylistId()), HttpStatus.OK);
    }

    // Notion test

    @GetMapping("/add-track")
    public ResponseEntity<String> addTrack() {
        return new ResponseEntity<>(
                notionService
                        .addTrack(new Song("Do I wanna know", List.of("Arctic Monkeys"), "AM", LocalDate.now()))
                        .toString(),
                HttpStatus.OK);
    }

    // OAuth things

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
            // store the access details for persistence
            JSON_MAPPER.writeValue(new File("access_token"), accessTokenResponse);
        } catch (IOException e) {
            logger.error("Failed to write access token file", e);
        }
        // also update our state
        spotifyProperties.setAccessTokenResponse(accessTokenResponse);
    }

    /**
     * @return base64 encoded client_id:client_secret
     */
    private String getAuthHeader() {
        String header = String.format("%s:%s", spotifyProperties.getClientId(), spotifyProperties.getClientSecret());
        return Base64.getEncoder().encodeToString(header.getBytes(StandardCharsets.UTF_8));
    }
}
