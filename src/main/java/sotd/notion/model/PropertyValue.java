package sotd.notion.model;

import com.fasterxml.jackson.annotation.*;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyValue {

    private final String id;

    private final PropertyType type;

    @JsonCreator
    public PropertyValue(
            @JsonProperty("id") String id,
            @JsonProperty("type") PropertyType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public PropertyType getType() {
        return type;
    }

    public enum PropertyType {

        RICH_TEXT("rich_text"),
        NUMBER("number"),
        SELECT("select"),
        MULTI_SELECT("multi_select"),
        DATE("date"),
        FORMULA("formula"),
        RELATION("relation"),
        ROLLUP("rollup"),
        TITLE("title"),
        PEOPLE("people"),
        FILES("files"),
        CHECKBOX("checkbox"),
        URL("url"),
        EMAIL("email"),
        PHONE_NUMBER("phone_number"),
        CREATED_TIME("created_time"),
        CREATED_BY("created_by"),
        LAST_EDITED_TIME("last_edited_time"),
        LAST_EDITED_BY("last_edited_by");

        private final String name;

        PropertyType(String name) {
            this.name = name;
        }

        @JsonValue
        public String getName() {
            return name;
        }
    }
}
