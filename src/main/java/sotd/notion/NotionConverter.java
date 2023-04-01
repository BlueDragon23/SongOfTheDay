package sotd.notion;

import org.springframework.stereotype.Component;
import sotd.adapter.ModelConverter;
import sotd.notion.model.PropertyValue;
import sotd.spotify.model.SimplifiedArtistObject;
import sotd.spotify.model.TrackObject;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NotionConverter implements ModelConverter<Map<String, PropertyValue>> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_INSTANT;

    @Override
    public Map<String, PropertyValue> toRecord(TrackObject trackObject) {
        Map<String, PropertyValue> properties = new HashMap<>();
        properties.put("Title", new PropertyValue(trackObject.getName(), PropertyValue.PropertyType.TITLE));
        properties.put("Artists", new PropertyValue(
                trackObject
                        .getArtists()
                        .stream()
                        .map(SimplifiedArtistObject::getName)
                        .collect(Collectors.joining(", ")),
                PropertyValue.PropertyType.RICH_TEXT));
        properties.put("Album", new PropertyValue(trackObject.getAlbum().getName(), PropertyValue.PropertyType.RICH_TEXT));
        properties.put("Date Added", new PropertyValue(FORMATTER.format(Instant.now()), PropertyValue.PropertyType.DATE));
        return properties;
    }

    @Override
    public TrackObject fromRecord(Map<String, PropertyValue> record) {
        throw new UnsupportedOperationException("No need to convert back to a track yet");
    }
}
