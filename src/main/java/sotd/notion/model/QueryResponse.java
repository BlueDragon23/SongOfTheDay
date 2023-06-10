package sotd.notion.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class QueryResponse {

    private final String object;
    private final List<Page> results;
    private final boolean hasMore;
    private final String nextCursor;

    @JsonCreator
    public QueryResponse(
            @JsonProperty("object") String object,
            @JsonProperty("results") List<Page> results,
            @JsonProperty("has_more") boolean hasMore,
            @JsonProperty("next_cursor") String nextCursor) {
        this.object = object;
        this.results = results;
        this.hasMore = hasMore;
        this.nextCursor = nextCursor;
    }

    public String getObject() {
        return object;
    }

    public List<Page> getResults() {
        return results;
    }

    public boolean hasMore() {
        return hasMore;
    }

    public String getNextCursor() {
        return nextCursor;
    }
}
