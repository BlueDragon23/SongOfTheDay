package sotd.notion.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {

    // Always page
    private final String object;
    private final String id;
    private final boolean archived;
    private final Map<String, PropertyValue> properties;
    private final Object parent;
    private final String url;

    @JsonCreator
    public Page(
            @JsonProperty("object") String object,
            @JsonProperty("id") String id,
            @JsonProperty("archived") boolean archived,
            @JsonProperty("properties") Map<String, PropertyValue> properties,
            @JsonProperty("parent") Object parent,
            @JsonProperty("url") String url) {
        this.object = object;
        this.id = id;
        this.archived = archived;
        this.properties = properties;
        this.parent = parent;
        this.url = url;
    }

    public String getObject() {
        return object;
    }

    public String getId() {
        return id;
    }

    public boolean isArchived() {
        return archived;
    }

    public Map<String, PropertyValue> getProperties() {
        return properties;
    }

    public Object getParent() {
        return parent;
    }

    public String getUrl() {
        return url;
    }
}
