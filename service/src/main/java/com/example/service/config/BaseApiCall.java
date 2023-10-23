package com.example.service.config;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class BaseApiCall {
    private final RestTemplate restClient;

    public BaseApiCall(RestTemplate restClient) {
        this.restClient = restClient;
    }

    public String get(final String baseApi, HttpHeaders headers, String path, Map<String, String> params) {
        ResponseEntity<String> responseEntity = restClient.exchange(
                genUrlComponent(baseApi, path, params).toUriString(),
                HttpMethod.GET, buildEntity("parameters", headers), String.class, params);

        return processResponse(responseEntity);
    }

    public String post(final String baseApi, HttpHeaders headers, String path,
                       Map<String, String> params, Object object) {
        ResponseEntity<String> responseEntity = restClient.exchange(
                genUrlComponent(baseApi, path, params).toUriString(),
                HttpMethod.POST, buildEntity(object, headers), String.class);

        return processResponse(responseEntity);
    }

    public String put(final String baseApi, HttpHeaders headers, String path, Map<String, String> params, Object object) {
        ResponseEntity<String> responseEntity = restClient.exchange(
                genUrlComponent(baseApi, path, params).toUriString(),
                HttpMethod.PUT, buildEntity(object, headers), String.class);

        return processResponse(responseEntity);
    }

    public String delete(final String baseApi, HttpHeaders headers, String path, Map<String, String> params, Object object) {
        ResponseEntity<String> responseEntity = restClient.exchange(
                genUrlComponent(baseApi, path, params).toUriString(),
                HttpMethod.DELETE, buildEntity(object, headers), String.class);

        return processResponse(responseEntity);
    }

    private UriComponents genUrlComponent(String baseApi, String path, Map<String, String> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseApi).path(path);
        if (params.size() > 0) {
            Set<String> keys = params.keySet();
            keys.forEach(key -> builder.queryParam(key, params.get(key)));
        }

        return builder.build();
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    private HttpEntity<Object> buildEntity(Object object, HttpHeaders headers) {
        if (headers == null)
            headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return new HttpEntity<>(object, headers);
    }

    public String processResponse(ResponseEntity<String> responseEntity) {
        if (responseEntity.getStatusCode().is2xxSuccessful())
            return responseEntity.getBody() == null ? "" : responseEntity.getBody();
        else
            return null;
    }
}
