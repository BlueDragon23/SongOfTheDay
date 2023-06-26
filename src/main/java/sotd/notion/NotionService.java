package sotd.notion;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import feign.Feign;
import feign.Logger;
import feign.http2client.Http2Client;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sotd.notion.model.Page;
import sotd.notion.model.QueryResponse;
import sotd.notion.model.UpdateDatabase;
import sotd.notion.model.propertyvalues.PropertyValue;
import sotd.notion.model.query.QueryDatabaseBody;
import sotd.notion.model.query.Sort;
import sotd.spotify.model.PlaylistTrackObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Handle interactions to sotd.notion
 */
@Component
public class NotionService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(NotionService.class);

    private final Notion notion;
    private final NotionConverter notionConverter;
    private final NotionProperties notionProperties;

    @Autowired
    public NotionService(NotionProperties notionProperties) {
        this.notionProperties = notionProperties;
        notion = Feign.builder()
                .requestInterceptor(input -> input.header("Authorization", "Bearer " + notionProperties.getSecretKey())
                        .header("Notion-Version", "2022-06-28"))
                .logger(new Logger.JavaLogger("Notion.Logger").appendToFile("logs/sotd.notion.log"))
                .logLevel(Logger.Level.FULL)
                .client(new Http2Client())
                .encoder(new JacksonEncoder(List.of(new Jdk8Module())))
                .decoder(new JacksonDecoder(List.of(new Jdk8Module())))
                .target(Notion.class, "https://api.notion.com");
        notionConverter = new NotionConverter();
    }

    public void addTrack(PlaylistTrackObject track) {
        Map<String, PropertyValue> pageProperties = notionConverter.toRecord(track);
        UpdateDatabase updateDatabase = new UpdateDatabase(pageProperties);
        notion.updateDatabase(updateDatabase, notionProperties.getDatabaseId());
        logger.info("Added track {}", track.track().name());
    }

    public List<Page> getTracks() {
        QueryDatabaseBody body = new QueryDatabaseBody().withSorts(List.of(new Sort("Date Added", "ascending")));
        QueryResponse response = notion.queryDatabase(body, notionProperties.getDatabaseId());
        List<Page> pages = new ArrayList<>(response.results());
        // pagination
        while (response.hasMore()) {
            body.withStartCursor(response.nextCursor());
            response = notion.queryDatabase(body, notionProperties.getDatabaseId());
            pages.addAll(response.results());
        }
        return pages;
    }
}
