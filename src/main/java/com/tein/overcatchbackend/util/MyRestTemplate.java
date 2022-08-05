package com.tein.overcatchbackend.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MyRestTemplate {

    static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) throws Exception {

        final MediaType mediaType = MediaType.APPLICATION_FORM_URLENCODED;
        final MediaType mediaType1 = MediaType.APPLICATION_JSON;
/*
        final Map<String, String> formParams = new HashMap<String, String>() {{
            put("api_key", "df1184-f6e584-16df45-aa3148-e05dcf");
            put("secret", "bedca4-782bf8-15dfd8-215e77-634dbb");
        }};

        ResultData resultData =  getTokenRequestWithFormData("https://www.hgskurumsal.com/api/auth",ResultData.class,mediaType,formParams,null);
        System.out.println(resultData);

*/
        final Map<String, String> headerParam = new HashMap<String, String>() {{
            put("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6Ijk5NjMiLCJuYW1lIjoiXHUwMTMwYnJhaGltIEF5ZFx1MDEzMW4iLCJlbWFpbCI6ImluZm9AdGVpbi5jb20udHIiLCJjcmVhdGVfZGF0ZSI6IjIwMjAtMDgtMDcgMjA6MTc6MjUifQ.et_eWNjLo4bfzGrnMT21UxvRpB0sUiYGtYOQne0ZerQ");

        }};

    }

    public <T> T getTokenRequest(final String url, final Class<T> returnTypeClass,
                                 final MediaType mediaTypes,
                                 final Map<String, String> headerParams,
                                 final Map<String, Object> autParams, final Map<String, Object> queryParams) throws Exception {

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaTypes);
        setHeaderParamsIfExists(headers, headerParams);

        JSONObject personJsonObject = new JSONObject();
        for (Map.Entry<String, Object> pair : autParams.entrySet()) {
            personJsonObject.put(pair.getKey(), pair.getValue());
        }
        final HttpEntity<String> requestEntity = new HttpEntity<>(personJsonObject.toString(), headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        setQueryParamsIfExists(uriBuilder, queryParams);

        final ResponseEntity<T> entity = restTemplate
                .exchange(getUrl(uriBuilder),
                        HttpMethod.POST,
                        requestEntity,
                        returnTypeClass);

        return entity.getBody();
    }
    public static <T> T getTokenRequestWithFormData(final String url, final Class<T> returnTypeClass,
                                             final MediaType mediaTypes,
                                             final Map<String, String> formParam, final Map<String, Object> queryParams) throws Exception {

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaTypes);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        for (Map.Entry<String, String> pair : formParam.entrySet()) {
            map.add(pair.getKey(),pair.getValue());
        }

        final HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        setQueryParamsIfExists(uriBuilder, queryParams);

        final ResponseEntity<T> entity = restTemplate
                .exchange(getUrl(uriBuilder),
                        HttpMethod.POST,
                        requestEntity,
                        returnTypeClass);

        return entity.getBody();
    }
    public static String getRequestWithRawBody(final String url, final String jsonStr,
                                               final MediaType mediaTypes,
                                               final Map<String, String> headerParams,
                                               final Map<String, Object> queryParams) throws Exception {

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(mediaTypes);
        setHeaderParamsIfExists(httpHeaders, headerParams);

        final HttpEntity<String> requestEntity = new HttpEntity<>(jsonStr, httpHeaders);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        setQueryParamsIfExists(uriBuilder, queryParams);

        final ResponseEntity<String> entity = restTemplate
                .exchange(getUrl(uriBuilder),
                        HttpMethod.POST,
                        requestEntity,
                        new ParameterizedTypeReference<String>() {
                        });
        return entity.getBody();
    }
    public static <T> T getRequestWithAutT(final String url, final Class<T> returnTypeClass,
                                                final MediaType mediaTypes,
                                                final Map<String, String> headerParams,
                                                final Map<String, Object> queryParams) throws Exception {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaTypes);
        setHeaderParamsIfExists(headers, headerParams);
        final HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        setQueryParamsIfExists(uriBuilder, queryParams);

        final ResponseEntity<T> entity = restTemplate
                .exchange(getUrl(uriBuilder),
                        HttpMethod.GET,
                        requestEntity,
                        returnTypeClass);
        return entity.getBody();
    }
    public static <T> List<T> getRequestWithAut(final String url, final Class<T> returnTypeClass,
                                                final MediaType mediaTypes,
                                                final Map<String, String> headerParams,
                                                final Map<String, Object> queryParams) throws Exception {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaTypes);
        setHeaderParamsIfExists(headers, headerParams);
        final HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        setQueryParamsIfExists(uriBuilder, queryParams);

        final ResponseEntity<List<T>> entity = restTemplate
                .exchange(getUrl(uriBuilder),
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<List<T>>() {
                        });
        return entity.getBody();
    }

    private static void setHeaderParamsIfExists(HttpHeaders headers, Map<String, String> headerParams) {
        if (headerParams != null && !headerParams.isEmpty())
            headerParams.entrySet()
                    .forEach(entry -> headers.set(entry.getKey(), entry.getValue()));
    }

    private static void setQueryParamsIfExists(UriComponentsBuilder uriBuilder, Map<String, Object> queryParams) {
        if (queryParams != null && !queryParams.isEmpty())
            queryParams.entrySet()
                    .forEach(entry -> uriBuilder.queryParam(entry.getKey(), entry.getValue()));
    }

    private static URI getUrl(UriComponentsBuilder uriBuilder) {
        return uriBuilder.build().encode().toUri();
    }

    /*  private HttpHeaders getHttpHeaders() {
          HttpHeaders headers = new HttpHeaders();
          headers.setContentType( MediaType.APPLICATION_JSON );
          headers.set( "X-RequestId", RequestUtility.createRequestId() );
          headers.set( "X-SourceSystem", mccmSource );
          headers.set( "X-User", mccmSource );
          headers.set( "X-ApplicationId", mccmSource );
          return headers;
      }*/


    public static <T> T ToObjectConverter(final String url, Class<? extends T> clazz,
                                          final MediaType mediaTypes,
                                          final Map<String, String> headerParams,
                                          final Map<String, Object> queryParams, boolean isFromJSON) {
        ObjectMapper mapper = new ObjectMapper();
        try {

            if (isFromJSON) {
                mapper = new ObjectMapper();
            } else {
                //  mapper = new XmlMapper();
            }
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            final HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(mediaTypes);
            setHeaderParamsIfExists(httpHeaders, headerParams);
            final HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
            setQueryParamsIfExists(uriBuilder, queryParams);

            final ResponseEntity<String> entity = restTemplate
                    .exchange(getUrl(uriBuilder),
                            HttpMethod.POST,
                            requestEntity,
                            new ParameterizedTypeReference<String>() {
                            });

            T objectTobeConverted = clazz.getConstructor().newInstance();
            objectTobeConverted = mapper.readValue(entity.getBody(), clazz);

            return objectTobeConverted;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T fileToObjectConverter(final String url, final String jsonStr, Class<? extends T> clazz,
                                              final MediaType mediaTypes,
                                              final Map<String, String> headerParams,
                                              final Map<String, Object> queryParams, boolean isFromJSON) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (isFromJSON) {
                mapper = new ObjectMapper();
            } else {
                //  mapper = new XmlMapper();
            }
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            final HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(mediaTypes);
            setHeaderParamsIfExists(httpHeaders, headerParams);
            final HttpEntity<String> requestEntity = new HttpEntity<>(jsonStr, httpHeaders);

            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
            setQueryParamsIfExists(uriBuilder, queryParams);

            final ResponseEntity<String> entity = restTemplate
                    .exchange(getUrl(uriBuilder),
                            HttpMethod.POST,
                            requestEntity,
                            new ParameterizedTypeReference<String>() {
                            });

            T objectTobeConverted = clazz.getConstructor().newInstance();
            objectTobeConverted = mapper.readValue(entity.getBody(), clazz);

            return objectTobeConverted;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}