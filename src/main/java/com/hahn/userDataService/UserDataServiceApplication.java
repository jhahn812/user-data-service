package com.hahn.userDataService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hahn.userDataService.models.UserView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class UserDataServiceApplication {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {

        SpringApplication.run(UserDataServiceApplication.class, args);

        // Method to print response to console in a more readable format
        // printUserData();
    }

    @SuppressWarnings("unused")
    private static void printUserData() {

        List<UserView> userViews = getUserViews();

        if (CollectionUtils.isEmpty(userViews)) {
            System.out.print("No users returned");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        userViews.forEach(r -> {

            String formattedJson = null;

            try {
                formattedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(r);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            System.out.println(formattedJson);
        });
    }

    private static List<UserView> getUserViews() {

        RestTemplate restTemplate = new RestTemplate();

        UserView[] userViews = restTemplate.getForObject("http://localhost:8080/users/all", UserView[].class);

        if (userViews == null) {
            return Collections.emptyList();
        }

        return Arrays.asList(userViews);
    }
}