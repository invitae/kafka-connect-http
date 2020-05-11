package uk.co.threefi.connect.http.converters;

import io.confluent.connect.avro.AvroConverter;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaAndValue;
import org.apache.kafka.connect.json.JsonConverter;
import org.apache.kafka.connect.storage.Converter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;

/**
 * Connect controller implementation that receives Avro-encoded data and
 * stores it internally as a JSON string.
 *
 * Use this if you want your HTTP sink connector to receive Avro and dump JSON
 * to the HTTP endpoint.
 */
public class AvroToJsonConverter implements Converter {
    Converter avroConverter = new AvroConverter();
    Converter jsonConverter = new JsonConverter();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
        avroConverter.configure(map, b);
        jsonConverter.configure(map, b);
    }

    /**
     * This method is not used by sinks, so no need for implementation.
     */
    @Override
    public byte[] fromConnectData(String s, Schema schema, Object o) {
        throw new NotImplementedException();
    }

    @Override
    public SchemaAndValue toConnectData(String s, byte[] bytes) {
        SchemaAndValue rawConnectStruct = avroConverter.toConnectData(s, bytes);
        byte[] json = jsonConverter.fromConnectData(s, rawConnectStruct.schema(), rawConnectStruct.value());
        return new SchemaAndValue(Schema.STRING_SCHEMA, new String(json));
    }
}
