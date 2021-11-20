package sotd.common.model;

import sotd.spotify.model.SimplifiedArtistObject;
import sotd.spotify.model.TrackObject;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Song {
    private final String title;
    private final List<String> artists;

    public Song(String title, List<String> artists) {
        this.title = title;
        this.artists = artists;
    }

    public static Song fromTrack(TrackObject track) {
        String title = track.getName();
        List<String> artists = track.getArtists().stream().map(SimplifiedArtistObject::getName).collect(Collectors.toList());
        return new Song(title, artists);
    }

    public String getTitle() {
        return title;
    }

    public List<String> getArtists() {
        return artists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return title.equals(song.title) && artists.equals(song.artists);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artists);
    }
}
