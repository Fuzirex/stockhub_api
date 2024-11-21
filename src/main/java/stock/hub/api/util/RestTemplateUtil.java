package stock.hub.api.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import stock.hub.api.configuration.logs.LogExecutionTime;

@Component
@RequiredArgsConstructor
public class RestTemplateUtil {

    private final RestTemplate restTemplate;

    @LogExecutionTime
    public <T> ResponseEntity<T> post(String url, Object payload, ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(url, HttpMethod.POST, createHttpEntity(payload), responseType);
    }

    @LogExecutionTime
    public <T> ResponseEntity<T> put(String url, Object payload, ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(url, HttpMethod.PUT, createHttpEntity(payload), responseType);
    }

    @LogExecutionTime
    public <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(url, HttpMethod.GET, createHttpEntity(null), responseType);
    }

    private <T> HttpEntity<?> createHttpEntity(T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(body, headers);
    }
}
