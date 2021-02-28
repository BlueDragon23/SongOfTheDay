package spotify.oauth;

import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

public interface SpotifyOAuth {
    @RequestLine("GET /authorize")
    Object authorize(@QueryMap Map<String, String> map);
}
