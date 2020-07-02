package vn.topica.utility.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class RestRequest {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public RestRequest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.restTemplate = new RestTemplate();
    }

    public <T> T get(String url, Map<String, Object> params, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder
            .fromHttpUrl(url);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }

        HttpEntity<?> entity = new HttpEntity<>(headers);
        restTemplate.getMessageConverters()
            .add(0, new StringHttpMessageConverter(UTF_8));

        HttpEntity<T> response = restTemplate.exchange(
            builder.build(false).toUriString(),
            HttpMethod.GET,
            entity,
            responseType);
        return response.getBody();
    }

    public <T> T post(String url, Object body, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> entity = new HttpEntity<>(body, headers);

        HttpEntity<T> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            responseType);
        return response.getBody();
    }


    public String post(String url, Map body) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(body, headers);

        HttpEntity<String> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            String.class);
        return response.getBody();
    }


    public <I> Object postAndGetData(String url, I body) {
        String response = post(url, objectMapper.convertValue(body, Map.class));
        JsonParser jsonParser  =  JsonParserFactory.getJsonParser();
        Map<String, Object> resJson = jsonParser.parseMap(response);
        return resJson.getOrDefault("data", null);
    }

    public Object postAndGetData(String url, Map body) {
        String response = post(url, body);
        JsonParser jsonParser  =  JsonParserFactory.getJsonParser();
        Map<String, Object> resJson = jsonParser.parseMap(response);
        return resJson.getOrDefault("data", null);
    }

    public <ResponseFromLearningService, I> ResponseFromLearningService get(String url, I objectParams, Class<ResponseFromLearningService> responseType) {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        Map<String, Object> map = objectMapper.convertValue(objectParams, Map.class);

        UriComponentsBuilder builder = UriComponentsBuilder
            .fromHttpUrl(url);

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }


        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<ResponseFromLearningService> response = restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.GET,
            entity,
            responseType);
        return response.getBody();
    }



    public <I> String get(String url, I objectParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        Map<String, Object> map = objectMapper.convertValue(objectParams, Map.class);

        UriComponentsBuilder builder = UriComponentsBuilder

            .fromHttpUrl(url);

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }


        HttpEntity<?> entity = new HttpEntity<>(headers);

        restTemplate.getMessageConverters()
            .add(0, new StringHttpMessageConverter(UTF_8));

        HttpEntity<String> response = restTemplate.exchange(
            builder.build(false).toUriString(),
            HttpMethod.GET,
            entity,
            String.class);
        return response.getBody();
    }

    public <I> Object getData(String url, I objectParams) {
        String response = get(url, objectParams);
        JsonParser jsonParser  =  JsonParserFactory.getJsonParser();
        Map<String, Object> resJson = jsonParser.parseMap(response);
        return resJson.getOrDefault("data", null);
    }


}
