package sotd.notion.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PageParent(@JsonProperty("type") String type, @JsonProperty("database_id") String id) {

    public PageParent(String id) {
        this("database_id", id);
    }
}
