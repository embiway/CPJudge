package com.embi.service.requestmappers;

import com.embi.service.DTO.CodeDTO;
import com.embi.service.DTO.VerdictDTO;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomJSONParser extends SimpleModule {
    public CustomJSONParser() {
        this.addDeserializer(CodeDTO.class, new JsonDeserializer<CodeDTO>() {
            @Override
            public CodeDTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
                ObjectCodec objectCodec = jsonParser.getCodec();
                JsonNode jsonNode = objectCodec.readTree(jsonParser);

                ObjectMapper mapper = new ObjectMapper();

                System.out.println("JSONNode = " + jsonNode.toString());

                CodeDTO code = new CodeDTO();
                code.setId(jsonNode.get("id").asInt());
                code.setProblemId(jsonNode.get("problemId").asInt(0));
                code.setLanguage(jsonNode.get("language").asText("cpp"));
                code.setIo(mapper.convertValue(jsonNode.get("io"), new TypeReference<>(){}));

                code.setActualCode(jsonNode.get("code").asText());
                code.setUseOutputVerifier(jsonNode.get("useOutputVerifier").asBoolean(false));
                if (code.isUseOutputVerifier()) code.setOutputVerifier(jsonNode.get("outputVerifier").asText());
                code.setProblemTimeLimit(jsonNode.get("timeLimit").asInt());
                code.setProblemMemoryLimit(jsonNode.get("memoryLimit").asInt());

                return code;
            }
        });

        this.addSerializer(VerdictDTO.class, new JsonSerializer<VerdictDTO>() {
            @Override
            public void serialize(VerdictDTO verdictDTO, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("result", verdictDTO.getResult());
                jsonGenerator.writeStringField("message", verdictDTO.getMessage());
                jsonGenerator.writeNumberField("codeId", verdictDTO.getCodeId());
                jsonGenerator.writeEndObject();
            }
        });
    }
}

