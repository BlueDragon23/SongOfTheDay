package sotd.notion.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateDatabase {

    // Always page
    private final Map<String, PropertyValue> properties;

    @JsonCreator
    public UpdateDatabase(@JsonProperty("properties") Map<String, PropertyValue> properties) {
        this.properties = properties;
    }

    public Map<String, PropertyValue> getProperties() {
        return properties;
    }
}
