package sotd.notion;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import sotd.adapter.ModelConverter;
import sotd.common.model.Song;
import sotd.notion.model.Page;
import sotd.notion.model.propertyvalues.DatePropertyValue;
import sotd.notion.model.propertyvalues.PropertyValue;
import sotd.notion.model.propertyvalues.RichTextPropertyValue;
import sotd.notion.model.propertyvalues.TitlePropertyValue;

@Component
public class NotionConverter implements ModelConverter<Page> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE;

    @Override
    public Page toRecord(Song song) {
        Map<String, PropertyValue> properties = new HashMap<>();
        properties.put("Title", new TitlePropertyValue(song.title()));
        properties.put("Artists", new RichTextPropertyValue(String.join(", ", song.artists())));
        properties.put("Album", new RichTextPropertyValue(song.album()));
        properties.put("Date Added", new DatePropertyValue(FORMATTER.format(song.addedAt())));
        return new Page(properties);
    }

    @Override
    public Song fromRecord(Page page) {
        TitlePropertyValue title = page.getPropertyValue("Title");
        RichTextPropertyValue album = page.getPropertyValue("Album");
        RichTextPropertyValue artists = page.getPropertyValue("Artists");
        DatePropertyValue dateAdded = page.getPropertyValue("Date Added");
        return new Song(
                title.getPlainText(),
                List.of(artists.getPlainText().split(", ")),
                album.getPlainText(),
                dateAdded.getDateObject().getStartDate());
    }
}
