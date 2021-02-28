package spotify;

import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import spotify.model.PlaylistObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        final String appConfigPath = rootPath + "app.properties";
        try (FileInputStream stream = new FileInputStream(appConfigPath)) {

            final Properties appProps = new Properties();
            appProps.load(stream);
            final String spotifyToken = appProps.getProperty("spotify");

            Spotify spotify = Feign.builder()
                    .requestInterceptor(input -> input.header("Authorization", spotifyToken))
                    .logger(new Logger.JavaLogger("Spotify.Logger").appendToFile("logs/spotify.log"))
                    .logLevel(Logger.Level.NONE)
                    .encoder(new JacksonEncoder())
                    .decoder(new JacksonDecoder())
                    .target(Spotify.class, "https://api.spotify.com");

            PlaylistObject playlist = spotify.getPlaylist("1MrpRM3ztd63yHWOW7fhIM");
            System.out.printf("%s: %s%n", playlist.getName(), playlist.getDescription());

        } catch (final IOException exception) {
            System.out.println("Failed to load properties file: " + exception.getMessage());
        }
    }
}
