package sotd.notion;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sotd.notion.model.Page;
import sotd.notion.model.PropertyValue;
import sotd.notion.model.QueryResponse;
import sotd.notion.model.UpdateDatabase;
import sotd.notion.model.query.QueryDatabaseBody;
import sotd.notion.model.query.Sort;
import sotd.spotify.model.TrackObject;

/**
 * Handle interactions to sotd.notion
 */
@Component
public class NotionService {

    @Value("${notion.database.id}")
    private String databaseId;

    @Value("${notion.secret.key}")
    private String secretKey;

    private final Notion notion;
    private final NotionConverter notionConverter;

    @Autowired
    public NotionService() {
        notion = Feign.builder()
                .requestInterceptor(input -> input.header("Authorization", secretKey))
                .logger(new Logger.JavaLogger("Notion.Logger").appendToFile("logs/sotd.notion.log"))
                .logLevel(Logger.Level.NONE)
                .encoder(new JacksonEncoder(List.of(new Jdk8Module())))
                .decoder(new JacksonDecoder(List.of(new Jdk8Module())))
                .target(Notion.class, "https://api.notion.com");
        notionConverter = new NotionConverter();
    }

    public void addTrack(TrackObject track) {
        Map<String, PropertyValue> pageProperties = notionConverter.toRecord(track);
        UpdateDatabase updateDatabase = new UpdateDatabase(pageProperties);
        notion.updateDatabase(updateDatabase, databaseId);
    }

    public List<Page> getTracks() {
        QueryDatabaseBody body = new QueryDatabaseBody().withSorts(List.of(new Sort("Date Added", "ascending")));
        QueryResponse response = notion.queryDatabase(body, databaseId);
        List<Page> pages = new ArrayList<>(response.getResults());
        // pagination
        while (response.hasMore()) {
            body.withStartCursor(response.getNextCursor());
            response = notion.queryDatabase(body, databaseId);
            pages.addAll(response.getResults());
        }
        return pages;
    }
}
