package sotd.common.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import sotd.spotify.model.PlaylistTrackObject;
import sotd.spotify.model.SimplifiedArtistObject;
import sotd.spotify.model.TrackObject;

public record Song(String title, List<String> artists, String album, LocalDate addedAt) {

    public static Song fromTrack(PlaylistTrackObject playlistTrack) {
        TrackObject track = playlistTrack.track();
        String title = track.name();
        List<String> artists =
                track.artists().stream().map(SimplifiedArtistObject::getName).collect(Collectors.toList());
        String album = playlistTrack.track().album().getName();
        return new Song(title, artists, album, playlistTrack.getAddedAt());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return title.equals(song.title) && artists.equals(song.artists) && album.equals(song.album);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artists);
    }
}
