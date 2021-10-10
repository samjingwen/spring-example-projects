package org.samjingwen.snsexample.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IOSPayloadFormatter implements PayloadFormatter {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public String format(String message) {
        Map<String, Object> appMessageMap = new HashMap<>();
        appMessageMap.put("alert", message);

        Map<String, Object> appleMessageMap = new HashMap<>();
        appleMessageMap.put("aps", appMessageMap);

        return objectMapper.writeValueAsString(appleMessageMap);
    }
}
