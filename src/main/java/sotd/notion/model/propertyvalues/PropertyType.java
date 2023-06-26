package sotd.notion.model.propertyvalues;

public enum PropertyType {
    RICH_TEXT(Constants.RICH_TEXT),
    NUMBER(Constants.NUMBER),
    SELECT(Constants.SELECT),
    MULTI_SELECT(Constants.MULTI_SELECT),
    DATE(Constants.DATE),
    FORMULA(Constants.FORMULA),
    RELATION(Constants.RELATION),
    ROLLUP(Constants.ROLLUP),
    TITLE(Constants.TITLE),
    PEOPLE(Constants.PEOPLE),
    FILES(Constants.FILES),
    CHECKBOX(Constants.CHECKBOX),
    URL(Constants.URL),
    EMAIL(Constants.EMAIL),
    PHONE_NUMBER(Constants.PHONE_NUMBER),
    CREATED_TIME(Constants.CREATED_TIME),
    CREATED_BY(Constants.CREATED_BY),
    LAST_EDITED_TIME(Constants.LAST_EDITED_TIME),
    LAST_EDITED_BY(Constants.LAST_EDITED_BY);

    private final String name;

    PropertyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * String constants. Should only be used when required for annotations that require compile time strings
     */
    public static final class Constants {
        public static final String RICH_TEXT = "rich_text";
        public static final String NUMBER = "number";
        public static final String SELECT = "select";
        public static final String MULTI_SELECT = "multi_select";
        public static final String DATE = "date";
        public static final String FORMULA = "formula";
        public static final String RELATION = "relation";
        public static final String ROLLUP = "rollup";
        public static final String TITLE = "title";
        public static final String PEOPLE = "people";
        public static final String FILES = "files";
        public static final String CHECKBOX = "checkbox";
        public static final String URL = "url";
        public static final String EMAIL = "email";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String CREATED_TIME = "created_time";
        public static final String CREATED_BY = "created_by";
        public static final String LAST_EDITED_TIME = "last_edited_time";
        public static final String LAST_EDITED_BY = "last_edited_by";
    }
}
