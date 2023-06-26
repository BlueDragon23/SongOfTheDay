package sotd.notion.model.propertyvalues;

import com.fasterxml.jackson.annotation.*;

@JsonAutoDetect
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = TitlePropertyValue.class, name = PropertyType.Constants.TITLE),
    @JsonSubTypes.Type(value = DatePropertyValue.class, name = PropertyType.Constants.DATE),
    @JsonSubTypes.Type(value = RichTextPropertyValue.class, name = PropertyType.Constants.RICH_TEXT)
})
public abstract class PropertyValue {

    private final String id;

    private final PropertyType type;

    /**
     * @param id Every value has an id
     * @param type Must be one of {@link PropertyType}
     */
    @JsonCreator
    public PropertyValue(@JsonProperty("id") String id, @JsonProperty("type") PropertyType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public PropertyType getType() {
        return type;
    }
}
