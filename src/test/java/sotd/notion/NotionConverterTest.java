package sotd.notion;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sotd.notion.model.propertyvalues.DatePropertyValue;
import sotd.notion.model.propertyvalues.PropertyValue;
import sotd.notion.model.propertyvalues.RichTextPropertyValue;
import sotd.notion.model.propertyvalues.TitlePropertyValue;
import sotd.spotify.model.ArtistObject;
import sotd.spotify.model.PlaylistTrackObject;
import sotd.spotify.model.SimplifedAlbumObject;
import sotd.spotify.model.TrackObject;

class NotionConverterTest {

    private final TrackObject track = new TrackObject(
            new SimplifedAlbumObject(null, null, List.of(), null, null, "AM", null, null, null),
            List.of(new ArtistObject(null, null, "Arctic Monkeys", null, null, List.of())),
            0,
            "",
            "",
            false,
            "Arabella",
            "",
            0,
            "",
            "");

    private final String addedAt = DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now());

    private final PlaylistTrackObject playlistTrack = new PlaylistTrackObject(addedAt, track);

    private NotionConverter notionConverter;

    @BeforeEach
    void setup() {
        notionConverter = new NotionConverter();
    }

    @Test
    void conversionWorks() {
        Map<String, PropertyValue> properties = notionConverter.toRecord(playlistTrack);

        assertThat(properties)
                .hasEntrySatisfying("Title", v -> assertThat(v)
                        .isInstanceOfSatisfying(TitlePropertyValue.class, t -> assertThat(t.getPlainText())
                                .isEqualTo("Arabella")))
                .hasEntrySatisfying("Artists", propertyValueEquals("Arctic Monkeys"))
                .hasEntrySatisfying("Album", propertyValueEquals("AM"))
                .hasEntrySatisfying("Date Added", v -> assertThat(v)
                        .isInstanceOfSatisfying(
                                DatePropertyValue.class,
                                d -> assertThat(d.getDateObject().start()).startsWith("202")));
    }

    private Consumer<PropertyValue> propertyValueEquals(String expected) {
        Consumer<RichTextPropertyValue> assertTextMatches =
                p -> assertThat(p.getPlainText()).isEqualTo(expected);
        return v -> assertThat(v).isInstanceOfSatisfying(RichTextPropertyValue.class, assertTextMatches);
    }
}
