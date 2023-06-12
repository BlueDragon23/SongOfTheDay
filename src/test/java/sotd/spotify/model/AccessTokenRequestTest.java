package sotd.spotify.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

class AccessTokenRequestTest {

    @Test
    void serialisesCorrectly() throws JsonProcessingException {
        // when
        AccessTokenRequest request = new AccessTokenRequest("code");

        // then
        String requestString = new JsonMapper().writeValueAsString(request);

        // verify
        assertThat(requestString)
                .isEqualTo(
                        "{\"grant_type\":\"authorization_code\",\"code\":\"code\",\"redirect_uri\":\"http://localhost:8080/callback\"}");
    }
}
