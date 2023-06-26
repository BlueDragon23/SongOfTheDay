package sotd.notion.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RichTextObject(@JsonProperty("type") String type, @JsonProperty("plain_text") String plainText) {}
