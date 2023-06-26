package sotd.spotify.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import static sotd.testutils.TestUtils.MAPPER;

import java.io.IOException;
import java.io.InputStream;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PlaylistObjectTest {

    @Test
    void deserialisationWorks() throws IOException {
        // when
        try (InputStream resourceStream = getClass().getResourceAsStream("/spotify-playlist.json")) {
            assertThat(resourceStream)
                    .describedAs("The input stream should be opened")
                    .isNotNull();

            // then
            PlaylistObject playlistObject = MAPPER.readValue(resourceStream, PlaylistObject.class);

            // verify
            assertThat(playlistObject).isNotNull();
            List<PlaylistTrackObject> tracks = playlistObject.tracks().items();
            assertThat(tracks).anySatisfy(playlistTrack -> {
                assertThat(playlistTrack.addedAt()).isEqualTo("2023-01-02T12:12:25Z");
                assertThat(playlistTrack.getAddedAt())
                        .hasYear(2023)
                        .hasMonth(Month.JANUARY)
                        .hasDayOfMonth(2);
                TrackObject track = playlistTrack.track();
                assertThat(track.name()).isEqualTo("The New Year");
                assertThat(track.album().getName()).isEqualTo("Transatlanticism (10th Anniversary Edition)");
                assertThat(track.artists())
                        .hasSize(1)
                        .first()
                        .returns("Death Cab for Cutie", from(ArtistObject::getName));
            });
        }
    }
}
