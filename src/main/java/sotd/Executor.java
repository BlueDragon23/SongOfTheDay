package sotd;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.inject.name.Named;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import sotd.spotify.Spotify;
import sotd.spotify.model.PlaylistObject;

import java.util.List;

public class Executor {

    private final String spotifyToken;
    private final String spotifyPlaylistId;

    public Executor(@Named("spotify.api.token") String spotifyToken, @Named("spotify.playlist.id") String spotifyPlaylistId) {
        this.spotifyToken = spotifyToken;
        this.spotifyPlaylistId = spotifyPlaylistId;
    }

    public void execute() {

        Spotify spotify = Feign.builder()
                .requestInterceptor(input -> input.header("Authorization", spotifyToken))
                .logger(new Logger.JavaLogger("Spotify.Logger").appendToFile("logs/sotd.spotify.log"))
                .logLevel(Logger.Level.NONE)
                .encoder(new JacksonEncoder(List.of(new Jdk8Module())))
                .decoder(new JacksonDecoder(List.of(new Jdk8Module())))
                .target(Spotify.class, "https://api.spotify.com");

        PlaylistObject playlist = spotify.getPlaylist(spotifyPlaylistId);
        System.out.printf("%s: %s%n", playlist.getName(), playlist.getDescription());
    }
}
