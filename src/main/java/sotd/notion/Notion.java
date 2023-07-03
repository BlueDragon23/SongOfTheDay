package sotd.notion;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import sotd.notion.model.CreatePageRequest;
import sotd.notion.model.Page;
import sotd.notion.model.QueryResponse;
import sotd.notion.model.UpdateDatabase;
import sotd.notion.model.query.QueryDatabaseBody;

/**
 * Notion API client
 */
public interface Notion {

    @RequestLine("POST /v1/databases/{database_id}/query")
    @Headers("Content-Type: application/json")
    QueryResponse queryDatabase(QueryDatabaseBody body, @Param("database_id") final String databaseId);

    @RequestLine("PATCH /v1/databases/{database_id}")
    @Headers("Content-Type: application/json")
    void updateDatabase(UpdateDatabase body, @Param("database_id") final String databaseId);

    @RequestLine("POST /v1/pages")
    @Headers("Content-Type: application/json")
    Page createPage(CreatePageRequest body);
}
