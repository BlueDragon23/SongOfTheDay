package sotd.spotify.model;

import static sotd.rest.SotdController.REDIRECT_URI;

import feign.form.FormProperty;

public class AccessTokenRequest {
    @FormProperty("grant_type")
    private String grantType = "authorization_code";

    @FormProperty("code")
    private String code;

    @FormProperty("redirect_uri")
    private String redirectUri = REDIRECT_URI;

    public AccessTokenRequest(String code) {
        this.code = code;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getCode() {
        return code;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}
