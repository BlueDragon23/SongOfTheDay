package sotd.notion.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record QueryResponse(String object, List<Page> results, boolean hasMore, String nextCursor) {

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
}
