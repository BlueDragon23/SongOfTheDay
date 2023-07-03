package sotd.notion.model;

import static sotd.testutils.TestUtils.getResourceAsString;

import java.io.IOException;
import java.util.Map;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import sotd.notion.model.propertyvalues.PropertyValue;
import sotd.notion.model.propertyvalues.RichTextPropertyValue;
import sotd.notion.model.propertyvalues.TitlePropertyValue;
import sotd.testutils.TestUtils;

class CreatePageRequestTest {

    @Test
    void serialisesCorrectly() throws IOException, JSONException {
        // when
        String parentId = "d9824bdc84454327be8b5b47500af6ce";
        Map<String, PropertyValue> properties = Map.of(
                "Name",
                new TitlePropertyValue("Tuscan Kale"),
                "Description",
                new RichTextPropertyValue("A dark green leafy vegetable"));
        CreatePageRequest createPageRequest = new CreatePageRequest(new PageParent(parentId), properties);

        // then
        String actual = TestUtils.MAPPER.writeValueAsString(createPageRequest);

        // verify
        String expected = getResourceAsString("/notion-create-page.json");
        JSONAssert.assertEquals(expected, actual, JSONCompareMode.NON_EXTENSIBLE);
    }
}
