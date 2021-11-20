package sotd.notion.model.query;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static sotd.testutils.TestUtils.MAPPER;

class QueryDatabaseBodyTest {

    @Test
    void testSerialisation() throws JsonProcessingException {
        QueryDatabaseBody queryDatabaseBody = new QueryDatabaseBody()
                .withSorts(List.of(new Sort("created", "descending")))
                .withStartCursor("asdf");

        String query = MAPPER.writeValueAsString(queryDatabaseBody);

        assertThat(query).contains("\"property\":\"created\"");
        assertThat(query).contains("\"direction\":\"descending\"");
        assertThat(query).contains("\"start_cursor\":\"asdf\"");
        assertThat(query).contains("\"page_size\":100");
    }
}