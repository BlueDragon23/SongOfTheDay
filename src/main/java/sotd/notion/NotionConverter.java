package sotd.notion;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import sotd.adapter.ModelConverter;
import sotd.notion.model.propertyvalues.DatePropertyValue;
import sotd.notion.model.propertyvalues.PropertyValue;
import sotd.notion.model.propertyvalues.RichTextPropertyValue;
import sotd.notion.model.propertyvalues.TitlePropertyValue;
import sotd.spotify.model.PlaylistTrackObject;
import sotd.spotify.model.SimplifiedArtistObject;
import sotd.spotify.model.TrackObject;

@Component
public class NotionConverter implements ModelConverter<Map<String, PropertyValue>> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_INSTANT;

    @Override
    public Map<String, PropertyValue> toRecord(PlaylistTrackObject playlistTrackObject) {
        TrackObject trackObject = playlistTrackObject.track();
        Map<String, PropertyValue> properties = new HashMap<>();
        properties.put("Title", new TitlePropertyValue(trackObject.name()));
        properties.put(
                "Artists",
                new RichTextPropertyValue(trackObject.artists().stream()
                        .map(SimplifiedArtistObject::getName)
                        .collect(Collectors.joining(", "))));
        properties.put("Album", new RichTextPropertyValue(trackObject.album().getName()));
        properties.put("Date Added", new DatePropertyValue(FORMATTER.format(Instant.now())));
        return properties;
    }

    @Override
    public PlaylistTrackObject fromRecord(Map<String, PropertyValue> record) {
        throw new UnsupportedOperationException("No need to convert back to a track yet");
    }
}
