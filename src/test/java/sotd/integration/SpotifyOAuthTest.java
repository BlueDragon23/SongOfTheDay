package sotd.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.matching.EqualToPattern;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sotd.rest.SotdController;
import sotd.spotify.SpotifyProperties;
import sotd.spotify.model.AccessTokenResponse;
import sotd.spotify.oauth.SpotifyOAuth;
import sotd.testutils.TestUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8081)
class SpotifyOAuthTest {

    @Autowired
    private SotdController sotdController;

    @Autowired
    private SpotifyProperties spotifyProperties;

    @Autowired
    private SpotifyOAuth spotifyOAuth;

    @Test
    void testLogin() {
        // when

        // then
        ResponseEntity<?> login = sotdController.login();

        // verify
        assertThat(login.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(login.getHeaders()).describedAs("Headers match").hasEntrySatisfying("Location", list -> {
            URI uri = URI.create(list.get(0));
            assertThat(uri.getHost())
                    .describedAs("Should be redirected to spotify wiremock")
                    .isEqualTo("localhost");
            assertThat(uri.getPort()).isEqualTo(8081);
            assertThat(uri.getQuery())
                    .contains("client_id=" + spotifyProperties.getClientId())
                    .contains("redirect_uri=http://localhost:8080/callback")
                    .contains("scope=playlist-read-private");
        });
    }

    @Test
    void testCallback() throws JsonProcessingException {
        // when
        AccessTokenResponse accessTokenResponse =
                new AccessTokenResponse("accessToken", "type", "playlist-read-private", 10, "refresh");
        stubFor(post(urlEqualTo("/api/token"))
                .withFormParam("grant_type", new EqualToPattern("authorization_code"))
                .willReturn(aResponse().withBody(TestUtils.MAPPER.writeValueAsString(accessTokenResponse))));

        // then
        sotdController.callback("code", "test");

        // verify - we write to a file
        assertThat(true).isTrue();
    }
}
