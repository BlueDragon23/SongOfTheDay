package sotd.notion.model.propertyvalues;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;
import java.util.stream.Collectors;
import sotd.notion.model.RichTextObject;

@JsonTypeName(PropertyType.Constants.TITLE)
public class TitlePropertyValue extends PropertyValue {

    private final List<RichTextObject> richTextObjects;

    @JsonCreator
    public TitlePropertyValue(
            @JsonProperty("id") String id, @JsonProperty("title") List<RichTextObject> richTextObjects) {
        super(id, PropertyType.TITLE);
        this.richTextObjects = richTextObjects;
    }

    public TitlePropertyValue(String plainText) {
        super(null, PropertyType.TITLE);
        this.richTextObjects = List.of(new RichTextObject("text", plainText));
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
