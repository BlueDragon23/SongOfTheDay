package sotd.spotify.model;

import static sotd.rest.SotdController.REDIRECT_URI;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccessTokenRequest {
    private final String grantType = "authorization_code";

    @SuppressWarnings("unused")
    private final String code;

    private final String redirectUri = REDIRECT_URI;

    public AccessTokenRequest(String code) {
        this.code = code;
    }
}
