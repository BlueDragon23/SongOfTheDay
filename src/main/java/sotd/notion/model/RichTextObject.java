package sotd.notion.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RichTextObject(@JsonProperty("type") String type, @JsonProperty("text") TextObject text) {

    public RichTextObject(String type, String plainText) {
        this(type, new TextObject(plainText));
    }

    public String plainText() {
        return text.content();
    }
}
