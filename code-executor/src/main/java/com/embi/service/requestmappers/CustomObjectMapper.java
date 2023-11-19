package com.embi.service.requestmappers;

import com.embi.service.requestmappers.CustomJSONParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        this.registerModule(new CustomJSONParser());
    }
}
