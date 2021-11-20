package sotd.notion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sotd.notion.model.PropertyValue;
import sotd.spotify.model.ArtistObject;
import sotd.spotify.model.SimplifedAlbumObject;
import sotd.spotify.model.TrackObject;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

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
            ""
    );

    private NotionConverter notionConverter;

    @BeforeEach
    void setup() {
        notionConverter = new NotionConverter();
    }

    @Test
    void conversionWorks() {
        Map<String, PropertyValue> properties = notionConverter.toRecord(track);

        assertThat(properties)
                .hasEntrySatisfying("Title", propertyValueEquals("Arabella"))
                .hasEntrySatisfying("Artists", propertyValueEquals("Arctic Monkeys"))
                .hasEntrySatisfying("Album", propertyValueEquals("AM"))
                .hasEntrySatisfying("Date Added", v -> assertThat(v.getId()).startsWith("2021"));
    }

    private Consumer<PropertyValue> propertyValueEquals(String expected) {
        return v -> assertThat(v.getId()).isEqualTo(expected);
    }
}