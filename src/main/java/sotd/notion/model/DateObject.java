package sotd.notion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Optional;

public record DateObject(
        @JsonProperty("start") String start,
        @JsonProperty("end") String end,
        @JsonProperty("time_zone") String timeZone) {

    private static final DateTimeFormatter OPTIONAL_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_DATE)
            .optionalStart()
            .appendLiteral('T')
            .append(DateTimeFormatter.ISO_TIME)
            .toFormatter();

    @JsonIgnore
    public LocalDate getStartDate() {
        return LocalDate.parse(start, OPTIONAL_TIME_FORMATTER);
    }

    @JsonIgnore
    public Optional<LocalDate> getEndDate() {
        return Optional.ofNullable(end).map(e -> LocalDate.parse(e, OPTIONAL_TIME_FORMATTER));
    }
}
