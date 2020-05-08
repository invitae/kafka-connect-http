package uk.co.threefi.connect.http.converters;

import io.confluent.connect.avro.AvroConverter;
import org.apache.kafka.connect.json.JsonConverter;

public class AvroToJsonConverter extends CombinedConverter {
    public AvroToJsonConverter() {
        super(new AvroConverter(), new JsonConverter());
    }
}
