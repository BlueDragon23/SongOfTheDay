package spotify;

import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import spotify.model.PlaylistObject;

public class Main {
    public static void main(String[] args) {
        Spotify spotify = Feign.builder()
                .requestInterceptor(new RequestInterceptor() {
                    @Override
                    public void apply(RequestTemplate input) {
                        input.header("Authorization", "SuperSecretAuthToken");
                    }
                })
                .logger(new Logger.JavaLogger("Spotify.Logger").appendToFile("logs/spotify.log"))
                .logLevel(Logger.Level.NONE)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(Spotify.class, "https://api.spotify.com");

        PlaylistObject playlist = spotify.getPlaylist("1MrpRM3ztd63yHWOW7fhIM");
        System.out.printf("%s: %s%n", playlist.getName(), playlist.getDescription());
    }
}
