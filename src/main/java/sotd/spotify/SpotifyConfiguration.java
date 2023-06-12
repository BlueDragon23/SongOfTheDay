package sotd.spotify;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import feign.Feign;
import feign.Logger;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import jakarta.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sotd.spotify.model.AccessTokenResponse;
import sotd.spotify.oauth.OAuthInterceptor;
import sotd.spotify.oauth.SpotifyOAuth;

@Configuration
public class SpotifyConfiguration {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SpotifyConfiguration.class);

    private final SpotifyProperties spotifyProperties;

    private final OAuthInterceptor oAuthInterceptor;

    @Autowired
    public SpotifyConfiguration(SpotifyProperties spotifyProperties, OAuthInterceptor oAuthInterceptor) {
        this.spotifyProperties = spotifyProperties;
        this.oAuthInterceptor = oAuthInterceptor;
    }

    @PostConstruct
    public void tryLoadAccessToken() {
        try {
            AccessTokenResponse accessTokenResponse =
                    new JsonMapper().readValue(Files.readAllBytes(Path.of("access_token")), AccessTokenResponse.class);
            spotifyProperties.setAccessTokenResponse(accessTokenResponse);
        } catch (Exception e) {
            logger.warn("Failed to load access token", e);
        }
    }

    @Bean
    public Spotify spotify() {
        return Feign.builder()
                .requestInterceptor(oAuthInterceptor)
                .logger(new Logger.JavaLogger("Spotify.Logger").appendToFile("logs/sotd.spotify.log"))
                .logLevel(Logger.Level.BASIC)
                .encoder(new JacksonEncoder(List.of(new Jdk8Module())))
                .decoder(new JacksonDecoder(List.of(new Jdk8Module())))
                .target(Spotify.class, "https://api.spotify.com");
    }

    @Bean
    public SpotifyOAuth spotifyOAuth() {
        return Feign.builder()
                .logger(new Logger.JavaLogger("SpotifyOAuth.Logger").appendToFile("logs/sotd.spotify.log"))
                .logLevel(Logger.Level.BASIC)
                .encoder(new FormEncoder())
                .decoder(new JacksonDecoder(List.of(new Jdk8Module())))
                .target(SpotifyOAuth.class, spotifyProperties.getAccountUrl());
    }
}
