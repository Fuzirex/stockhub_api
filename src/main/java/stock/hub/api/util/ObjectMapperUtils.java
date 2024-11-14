package stock.hub.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class ObjectMapperUtils {

    // Mapper like the one configured in the yml, but just to work with toString() in DTOs, for the @LogExecutionTime
    public static final ObjectMapper OBJECT_MAPPER_DTO_TO_STRING = JsonMapper.builder()
            .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE,
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS,
                    SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
            .enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID)
            .addModule(new JavaTimeModule())
            .build();

    public static String writeValueAsString(Object obj) throws JsonProcessingException {
        return OBJECT_MAPPER_DTO_TO_STRING.writeValueAsString(obj);
    }

}
