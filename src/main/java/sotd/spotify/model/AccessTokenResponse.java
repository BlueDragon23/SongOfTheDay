package sotd.spotify.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * @param expiresIn Expiry period in seconds
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AccessTokenResponse(
        String accessToken, String tokenType, String scope, int expiresIn, String refreshToken) {
    public AccessTokenResponse(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("token_type") String tokenType,
            @JsonProperty("scope") String scope,
            @JsonProperty("expires_in") int expiresIn,
            @JsonProperty("refresh_token") String refreshToken) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.scope = scope;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }
}
