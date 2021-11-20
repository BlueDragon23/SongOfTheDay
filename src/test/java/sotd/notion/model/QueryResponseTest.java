package sotd.notion.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static sotd.testutils.TestUtils.MAPPER;

class QueryResponseTest {

    @Test
    void testDeserialisation() throws JsonProcessingException {
        String responseText = "{\n" +
                "  \"object\": \"list\",\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"object\": \"page\",\n" +
                "      \"id\": \"2e01e904-febd-43a0-ad02-8eedb903a82c\",\n" +
                "      \"created_time\": \"2020-03-17T19:10:04.968Z\",\n" +
                "      \"last_edited_time\": \"2020-03-17T21:49:37.913Z\",\n" +
                "      \"parent\": {\n" +
                "        \"type\": \"database_id\",\n" +
                "        \"database_id\": \"897e5a76-ae52-4b48-9fdf-e71f5945d1af\"\n" +
                "      },\n" +
                "      \"archived\": false,\n" +
                "      \"url\": \"https://www.notion.so/2e01e904febd43a0ad028eedb903a82c\",\n" +
                "      \"properties\": {\n" +
                "        \"Recipes\": {\n" +
                "          \"id\": \"Ai`L\",\n" +
                "          \"type\": \"relation\",\n" +
                "          \"relation\": [\n" +
                "            {\n" +
                "              \"id\": \"796659b4-a5d9-4c64-a539-06ac5292779e\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": \"79e63318-f85a-4909-aceb-96a724d1021c\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        \"Cost of next trip\": {\n" +
                "          \"id\": \"R}wl\",\n" +
                "          \"type\": \"formula\",\n" +
                "          \"formula\": {\n" +
                "            \"type\": \"number\",\n" +
                "            \"number\": 2\n" +
                "          }\n" +
                "        },\n" +
                "        \"Last ordered\": {\n" +
                "          \"id\": \"UsKi\",\n" +
                "          \"type\": \"date\",\n" +
                "          \"date\": {\n" +
                "            \"start\": \"2020-10-07\",\n" +
                "            \"end\": null\n" +
                "          }\n" +
                "        },\n" +
                "        \"In stock\": {\n" +
                "          \"id\": \"{>U;\",\n" +
                "          \"type\": \"checkbox\",\n" +
                "          \"checkbox\": false\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"has_more\": false,\n" +
                "  \"next_cursor\": null\n" +
                "}";
        QueryResponse response = MAPPER.readValue(responseText, QueryResponse.class);

        assertThat(response.getNextCursor()).isNull();
        assertThat(response.hasMore()).isFalse();
        assertThat(response.getObject()).isEqualTo("list");
        assertThat(response.getResults()).hasSize(1);
    }

    @Test
    void testDeserialisationPaginated() throws JsonProcessingException {
        String responseText = "{\n" +
                "  \"object\": \"list\",\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"object\": \"page\",\n" +
                "      \"id\": \"2e01e904-febd-43a0-ad02-8eedb903a82c\",\n" +
                "      \"created_time\": \"2020-03-17T19:10:04.968Z\",\n" +
                "      \"last_edited_time\": \"2020-03-17T21:49:37.913Z\",\n" +
                "      \"parent\": {\n" +
                "        \"type\": \"database_id\",\n" +
                "        \"database_id\": \"897e5a76-ae52-4b48-9fdf-e71f5945d1af\"\n" +
                "      },\n" +
                "      \"archived\": false,\n" +
                "      \"url\": \"https://www.notion.so/2e01e904febd43a0ad028eedb903a82c\",\n" +
                "      \"properties\": {\n" +
                "        \"Recipes\": {\n" +
                "          \"id\": \"Ai`L\",\n" +
                "          \"type\": \"relation\",\n" +
                "          \"relation\": [\n" +
                "            {\n" +
                "              \"id\": \"796659b4-a5d9-4c64-a539-06ac5292779e\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": \"79e63318-f85a-4909-aceb-96a724d1021c\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        \"Cost of next trip\": {\n" +
                "          \"id\": \"R}wl\",\n" +
                "          \"type\": \"formula\",\n" +
                "          \"formula\": {\n" +
                "            \"type\": \"number\",\n" +
                "            \"number\": 2\n" +
                "          }\n" +
                "        },\n" +
                "        \"Last ordered\": {\n" +
                "          \"id\": \"UsKi\",\n" +
                "          \"type\": \"date\",\n" +
                "          \"date\": {\n" +
                "            \"start\": \"2020-10-07\",\n" +
                "            \"end\": null\n" +
                "          }\n" +
                "        },\n" +
                "        \"In stock\": {\n" +
                "          \"id\": \"{>U;\",\n" +
                "          \"type\": \"checkbox\",\n" +
                "          \"checkbox\": false\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"has_more\": true,\n" +
                "  \"next_cursor\": \"asdf\"\n" +
                "}";
        QueryResponse response = MAPPER.readValue(responseText, QueryResponse.class);

        assertThat(response.getNextCursor()).isEqualTo("asdf");
        assertThat(response.hasMore()).isTrue();
        assertThat(response.getObject()).isEqualTo("list");
        assertThat(response.getResults()).hasSize(1);
    }
}