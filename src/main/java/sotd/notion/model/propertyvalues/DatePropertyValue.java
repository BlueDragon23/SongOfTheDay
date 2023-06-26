package sotd.notion.model.propertyvalues;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import sotd.notion.model.DateObject;

@JsonAutoDetect
public class DatePropertyValue extends PropertyValue {

    private final DateObject dateObject;

    @JsonCreator
    public DatePropertyValue(@JsonProperty("id") String id, @JsonProperty("date") DateObject dateObject) {
        super(id, PropertyType.DATE);
        this.dateObject = dateObject;
    }

    public DatePropertyValue(String start) {
        super(null, PropertyType.DATE);
        this.dateObject = new DateObject(start, null, null);
    }

    public DateObject getDateObject() {
        return dateObject;
    }

    @Override
    public String toString() {
        return "DatePropertyValue{" + "dateObject=" + dateObject + '}';
    }
}
