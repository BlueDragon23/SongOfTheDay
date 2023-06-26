package sotd.notion.model;

import static org.assertj.core.api.Assertions.assertThat;
import static sotd.testutils.TestUtils.MAPPER;

import java.io.IOException;
import java.io.InputStream;
import java.time.Month;
import org.junit.jupiter.api.Test;
import sotd.notion.model.propertyvalues.DatePropertyValue;
import sotd.notion.model.propertyvalues.RichTextPropertyValue;
import sotd.notion.model.propertyvalues.TitlePropertyValue;

class QueryResponseTest {

    @Test
    void testDeserialisation() throws IOException { // when
        try (InputStream resourceStream = getClass().getResourceAsStream("/notion-database.json")) {
            // when
            assertThat(resourceStream)
                    .describedAs("The input stream should be opened")
                    .isNotNull();

            // then
            QueryResponse response = MAPPER.readValue(resourceStream, QueryResponse.class);

            // verify
            assertThat(response.nextCursor()).isNull();
            assertThat(response.hasMore()).isFalse();
            assertThat(response.object()).isEqualTo("list");
            assertThat(response.results()).first().satisfies(p -> {
                TitlePropertyValue title = p.getPropertyValue("Title");
                assertThat(title.getPlainText()).isEqualTo("Test");
                DatePropertyValue dateAdded = p.getPropertyValue("Date Added");
                assertThat(dateAdded.getDateObject().getStartDate())
                        .hasYear(2023)
                        .hasMonth(Month.JUNE)
                        .hasDayOfMonth(20);
                RichTextPropertyValue artists = p.getPropertyValue("Artists");
                assertThat(artists.getPlainText()).isEqualTo("First");
            });
        }
    }
}
