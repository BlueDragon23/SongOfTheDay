package sotd.notion.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import sotd.notion.model.propertyvalues.PropertyValue;

/**
 * @param object Always "page"
 */
@JsonAutoDetect
public record Page(
        String object,
        String id,
        boolean archived,
        Map<String, PropertyValue> properties,
        PageParent parent,
        String url) {

    @JsonCreator
    public Page(
            @JsonProperty("object") String object,
            @JsonProperty("id") String id,
            @JsonProperty("archived") boolean archived,
            @JsonProperty("properties") Map<String, PropertyValue> properties,
            @JsonProperty("parent") PageParent parent,
            @JsonProperty("url") String url) {
        this.object = object;
        this.id = id;
        this.archived = archived;
        this.properties = properties;
        this.parent = parent;
        this.url = url;
    }

    /**
     * Attempt to get a typed property value
     * @param key The property key
     * @return The property value
     * @param <T> The type of property value
     * @throws ClassCastException if the value is of a different type, or if the key doesn't exist
     */
    @JsonIgnore
    @SuppressWarnings("unchecked")
    public <T extends PropertyValue> T getPropertyValue(String key) {
        return (T) properties.get(key);
    }
}
