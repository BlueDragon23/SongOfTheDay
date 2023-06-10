package sotd.spotify.oauth;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import sotd.spotify.model.AccessTokenRequest;
import sotd.spotify.model.AccessTokenResponse;

public interface SpotifyOAuth {

    @RequestLine("POST /api/token")
    @Headers({"Authorization: Basic {auth}", "Content-Type: application/x-www-form-urlencoded"})
    AccessTokenResponse token(AccessTokenRequest accessTokenRequest, @Param("auth") String auth);
}
