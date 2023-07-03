package sotd.notion;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sotd.common.model.Song;
import sotd.notion.model.propertyvalues.DatePropertyValue;
import sotd.notion.model.propertyvalues.PropertyValue;
import sotd.notion.model.propertyvalues.RichTextPropertyValue;
import sotd.notion.model.propertyvalues.TitlePropertyValue;

class NotionConverterTest {

    private final Song song = new Song("Arabella", List.of("Arctic Monkeys"), "AM", LocalDate.now());

    private NotionConverter notionConverter;

    @BeforeEach
    void setup() {
        notionConverter = new NotionConverter();
    }

    @Test
    void conversionWorks() {
        Map<String, PropertyValue> properties = notionConverter.toRecord(song).properties();

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
