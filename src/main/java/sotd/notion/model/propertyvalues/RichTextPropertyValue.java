package sotd.notion.model.propertyvalues;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import sotd.notion.model.RichTextObject;

@JsonAutoDetect
public class RichTextPropertyValue extends PropertyValue {

    @JsonProperty("rich_text")
    private final List<RichTextObject> richTextObjects;

    @JsonCreator
    public RichTextPropertyValue(
            @JsonProperty("id") String id, @JsonProperty("rich_text") List<RichTextObject> richTextObjects) {
        super(id, PropertyType.RICH_TEXT);
        this.richTextObjects = richTextObjects;
    }

    public RichTextPropertyValue(String plainText) {
        this(null, List.of(new RichTextObject("text", plainText)));
    }

    public List<RichTextObject> getRichTextObjects() {
        return richTextObjects;
    }

    @JsonIgnore
    public String getPlainText() {
        return richTextObjects.stream().map(RichTextObject::plainText).collect(Collectors.joining());
    }

    @Override
    public String toString() {
        return getPlainText();
    }
}
