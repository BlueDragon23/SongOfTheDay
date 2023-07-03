package sotd.common.model;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

public class JacksonTypeFilter {

    public static SimpleBeanPropertyFilter getTypeFilter() {
        return SimpleBeanPropertyFilter.serializeAllExcept("type");
    }
}
