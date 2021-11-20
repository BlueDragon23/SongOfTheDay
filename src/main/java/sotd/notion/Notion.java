package sotd.notion;

import feign.Param;
import feign.RequestLine;
import sotd.notion.model.UpdateDatabase;
import sotd.notion.model.QueryResponse;
import sotd.notion.model.query.QueryDatabaseBody;

/**
 * Notion API client
 */
public interface Notion {

    @RequestLine("POST /v1/databases/{database_id}/query")
    QueryResponse queryDatabase(QueryDatabaseBody body, @Param("database_id") final String databaseId);

    @RequestLine("PATCH /v1/databases/{database_id}")
    void updateDatabase(UpdateDatabase body, @Param("database_id") final String databaseId);
}
