package sotd.notion.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import sotd.notion.model.propertyvalues.PropertyValue;

public record CreatePageRequest(
        @JsonProperty("parent") PageParent parent,
        @JsonProperty("properties") Map<String, PropertyValue> propertyValues) {}
