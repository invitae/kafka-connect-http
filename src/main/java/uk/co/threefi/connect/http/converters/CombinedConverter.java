package uk.co.threefi.connect.http.converters;


import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaAndValue;
import org.apache.kafka.connect.storage.Converter;

import java.util.Map;

public class CombinedConverter implements Converter {
    Converter inputConverter;
    Converter outputConverter;

    public CombinedConverter(Converter inputConverter, Converter outputConverter) {
        this.inputConverter = inputConverter;
        this.outputConverter = outputConverter;
    }

    @Override
    public void configure(Map<String, ?> map, boolean b) {
        inputConverter.configure(map, b);
        outputConverter.configure(map, b);
    }

    @Override
    public byte[] fromConnectData(String s, Schema schema, Object o) {
        return outputConverter.fromConnectData(s, schema, o);
    }

    @Override
    public SchemaAndValue toConnectData(String s, byte[] bytes) {
        return inputConverter.toConnectData(s, bytes);
    }
}
