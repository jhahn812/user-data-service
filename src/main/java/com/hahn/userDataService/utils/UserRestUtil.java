package com.hahn.userDataService.utils;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class UserRestUtil {

    public static final String BASE_USER_URL = "https://5c3ce12c29429300143fe570.mockapi.io/api";
    public static final String REGISTERED_USERS_URL = BASE_USER_URL + "/registeredusers";
    public static final String UNREGISTERED_USERS_URL = BASE_USER_URL + "/unregisteredusers";
    public static final String PROJECT_MEMBERSHIPS_URL = BASE_USER_URL + "/projectmemberships";

    public static <T> HttpEntity<T> getHttpEntity() {
        return new HttpEntity<>(new HttpHeaders());
    }

    public static <T> List<T> getUserData(String url, RestTemplate restTemplate) {

        HttpEntity<String> httpEntity = UserRestUtil.getHttpEntity();

        ResponseEntity<List<T>> exchange = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });

        return exchange.getBody();
    }
}