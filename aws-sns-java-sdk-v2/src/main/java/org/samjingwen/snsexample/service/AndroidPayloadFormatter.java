package org.samjingwen.snsexample.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AndroidPayloadFormatter implements PayloadFormatter {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public String format(String message) {
        Map<String, String> payload = new HashMap<>();
        payload.put("message", message);

        Map<String, Object> androidMessageMap = new HashMap<>();
        androidMessageMap.put("data", payload);

        return objectMapper.writeValueAsString(androidMessageMap);
    }
}
