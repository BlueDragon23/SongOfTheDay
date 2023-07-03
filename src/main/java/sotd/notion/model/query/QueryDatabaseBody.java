package sotd.notion.model.query;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Optional;

@JsonAutoDetect
public class QueryDatabaseBody {

    private Optional<List<Sort>> sorts;

    @JsonProperty("start_cursor")
    private String startCursor;

    @JsonProperty("page_size")
    private final int pageSize = 100;

    public QueryDatabaseBody() {
        this.sorts = Optional.empty();
    }

    public Optional<List<Sort>> getSorts() {
        return sorts;
    }

    public QueryDatabaseBody withSorts(List<Sort> sorts) {
        this.sorts = Optional.of(sorts);
        return this;
    }

    public QueryDatabaseBody withStartCursor(String startCursor) {
        this.startCursor = startCursor;
        return this;
    }

    public String getStartCursor() {
        return startCursor;
    }

    public int getPageSize() {
        return pageSize;
    }
}
