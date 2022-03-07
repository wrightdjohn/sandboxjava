package com.sandbox.allsystemutils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandbox.allsystemutils.exceptions.JsonParsingException;
import java.io.IOException;
import spark.ResponseTransformer;

public class JsonMapper implements ResponseTransformer {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String render(Object o) throws Exception {
        return objectMapper.writeValueAsString(o);
    }

    public Object parse(String json, Class clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new JsonParsingException("Following Json was not parsable: "+json);
        }
    }
}
