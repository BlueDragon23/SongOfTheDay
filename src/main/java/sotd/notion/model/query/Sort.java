package sotd.notion.model.query;

public class Sort {

    private final String property;
    /** Must be "ascending" or "descending" */
    private final String direction;

    public Sort(String property, String direction) {
        this.property = property;
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public String getDirection() {
        return direction;
    }
}
