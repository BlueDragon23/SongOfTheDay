package sotd.spotify;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sotd.spotify.oauth.SpotifyOAuth;

import java.util.List;

@Configuration
public class SpotifyConfiguration {

    public static final String SPOTIFY_ACCOUNTS_URL = "https://accounts.spotify.com";
    private final SpotifyProperties spotifyProperties;

    @Autowired
    public SpotifyConfiguration(SpotifyProperties spotifyProperties) {
        this.spotifyProperties = spotifyProperties;
    }

    @Bean
    public Spotify spotify() {
        return Feign.builder()
                .requestInterceptor(input -> input.header("Authorization", ""))
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
                .encoder(new JacksonEncoder(List.of(new Jdk8Module())))
                .decoder(new JacksonDecoder(List.of(new Jdk8Module())))
                .target(SpotifyOAuth.class, SPOTIFY_ACCOUNTS_URL);
    }
}
