package com.kadir.common.utils.openai;

import com.kadir.common.dto.openai.OpenAiResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class OpenAiUtil {

    @Value("${openai.api.key}")
    private String OPENAI_API_KEY;

    /**
     * Creates a request body for OpenAI API.
     *
     * @param messages A list of message maps containing role and content.
     * @return A map representing the request body.
     */
    public Map<String, Object> createRequestBody(List<Map<String, String>> messages) {
        return Map.of(
                "model", "gpt-3.5-turbo",
                "messages", messages
        );
    }

    /**
     * Makes an API call to OpenAI.
     *
     * @param url         The API endpoint.
     * @param requestBody The request body as a map.
     * @return The response from the API as an OpenAiResponseDto.
     */
    public OpenAiResponseDto makeApiCall(String url, Map<String, Object> requestBody, WebClient webClient) {
        return webClient.post()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + OPENAI_API_KEY)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(OpenAiResponseDto.class)
                .block();
    }
}