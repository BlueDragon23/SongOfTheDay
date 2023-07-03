package sotd.testutils;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TestUtils {

    public static ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new Jdk8Module())
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public static String getResourceAsString(String name) throws IOException {
        try (InputStream resourceStream = TestUtils.class.getResourceAsStream(name)) {
            assertThat(resourceStream)
                    .describedAs("The input stream should be opened")
                    .isNotNull();

            assert resourceStream != null;
            return new String(resourceStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
