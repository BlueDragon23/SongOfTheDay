package sotd.notion.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TextObject(@JsonProperty("content") String content) {}
