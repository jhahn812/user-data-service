package com.hahn.userDataService

import groovy.json.JsonSlurperClassic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.ContentResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

@AutoConfigureMockMvc
@WebMvcTest
class UserDataServiceApplicationTests extends Specification {

    @Autowired
    private MockMvc mvc

    def "When All User Data is Retrieved"() {
        expect: "Status is 200 and Content Type is Application/JSON"

        def response = mvc.perform(MockMvcRequestBuilders.get("/users/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(new ContentResultMatchers().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .response

        and: "The response data is correct"

        response != null
        def users = new JsonSlurperClassic().parseText(response.getContentAsString()) as List<LinkedHashMap>
        users != null

        and: "The registered user data is valid"

        def registeredUsers = users.findAll { u -> u.lastName } as List<LinkedHashMap>
        registeredUsers.each { u -> isRegisteredUserDataValid(u as LinkedHashMap) }

        and: "The un-registered user data is valid"

        def unregisteredUsers = users.findAll { u -> u.registrationId } as List<LinkedHashMap>
        unregisteredUsers.each { u -> isUnregisteredUserDataValid(u as LinkedHashMap) }
    }

    private static void isRegisteredUserDataValid(LinkedHashMap data) {
        isCommonUserDataValid(data)
        assert data.lastName != null
        assert data.firstName != null
        assert data.city != null
        assert data.state != null
        assert data.zipCode != null
        assert data.country != null
        assert data.organizationType != null
        assert data.company != null
        assert data.phone != null
        assert data.disclaimerAccepted != null
    }

    private static void isUnregisteredUserDataValid(LinkedHashMap data) {
        isCommonUserDataValid(data)
        assert data.registrationId != null
        assert data.registrationIdGeneratedTime != null
    }

    private static void isCommonUserDataValid(LinkedHashMap data) {
        assert data.id != null
        assert data.emailAddress != null
        assert data.languageCode != null
        assert data.projectIds != null
    }

}
