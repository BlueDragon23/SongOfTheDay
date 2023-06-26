package sotd.notion.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import sotd.notion.model.propertyvalues.PropertyValue;

/**
 * @param properties Always page
 */
@JsonAutoDetect
public record UpdateDatabase(Map<String, PropertyValue> properties) {

    @JsonCreator
    public UpdateDatabase(@JsonProperty("properties") Map<String, PropertyValue> properties) {
        this.properties = properties;
    }
}
