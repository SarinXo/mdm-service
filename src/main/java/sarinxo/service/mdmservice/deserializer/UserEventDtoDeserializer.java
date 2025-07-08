package sarinxo.service.mdmservice.deserializer;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.common.InvalidRecordException;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.Deserializer;
import sarinxo.service.mdmservice.dto.UserEventDto;
import sarinxo.service.mdmservice.exception.ConfigResourceNotFound;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class UserEventDtoDeserializer implements Deserializer<UserEventDto> {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final Schema schema;

    /**
     * Читает avro схему
     */
    public UserEventDtoDeserializer() {
        try (InputStream is = getClass().getResourceAsStream("/avro/MDM.ChangePhone.IN.V1.avsc")) {
            if (is == null) {
                throw new FileNotFoundException("AVSC schema not found");
            }

            this.schema = new Schema.Parser().parse(is);
        } catch (IOException e) {
            throw new ConfigResourceNotFound("Failed loading AVRO schema", e);
        }
    }


    /**
     * Десериализует и проверяет на валидность значение. Валидация с помощью JSR 303.
     */
    @Override
    public UserEventDto deserialize(String topic, byte[] data) {
        try {
            if (data == null || data.length == 0) {
                throw new InvalidRecordException("Message data is null or empty");
            }

            DatumReader<UserEventDto> reader = new SpecificDatumReader<>(UserEventDto.class);
            var decoder = DecoderFactory.get().binaryDecoder(new ByteArrayInputStream(data), null);
            UserEventDto dto = reader.read(null, decoder);

            Set<ConstraintViolation<UserEventDto>> violations = validator.validate(dto);
            if (!violations.isEmpty()) {
                StringBuilder sb = new StringBuilder("Validation failed: ");

                for (ConstraintViolation<UserEventDto> v : violations) {
                    sb.append(v.getPropertyPath()).append(" ").append(v.getMessage()).append(";\n");
                }

                throw new InvalidRecordException(sb.toString());
            }

            return dto;
        } catch (Exception e) {
            throw new KafkaException("Failed to deserialize message: ", e);
        }
    }

}
