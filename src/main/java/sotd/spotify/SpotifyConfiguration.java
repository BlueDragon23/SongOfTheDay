package sotd.spotify;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sotd.spotify.oauth.SpotifyOAuth;

@Configuration
public class SpotifyConfiguration {

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
                .target(SpotifyOAuth.class, spotifyProperties.getAccountUrl());
    }
}
