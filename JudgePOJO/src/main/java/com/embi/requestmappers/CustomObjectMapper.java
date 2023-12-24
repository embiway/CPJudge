package com.embi.requestmappers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        this.registerModule(new CustomJSONParser());
    }
}
