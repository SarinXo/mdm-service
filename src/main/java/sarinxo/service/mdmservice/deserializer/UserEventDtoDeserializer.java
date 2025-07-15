package sarinxo.service.mdmservice.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.InvalidRecordException;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.kafka.support.serializer.DeserializationException;
import sarinxo.service.mdmservice.dto.UserEventDto;

@Slf4j
public class UserEventDtoDeserializer implements Deserializer<UserEventDto> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public UserEventDto deserialize(String topic, byte[] data) {
        try {
            if (data == null || data.length == 0) {
                throw new InvalidRecordException("Message data is null or empty");
            }

            return mapper.readValue(data, UserEventDto.class);

        } catch (Exception e) {
            String errMessage = "Failed to deserialize message in topic [" + topic + "]";
            log.error(errMessage);
            throw new DeserializationException(errMessage, data, false, e);
        }
    }

}
