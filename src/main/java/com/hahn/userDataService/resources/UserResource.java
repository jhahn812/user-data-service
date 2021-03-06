package com.hahn.userDataService.resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hahn.userDataService.models.ProjectMembershipView;
import com.hahn.userDataService.models.RegisteredUserView;
import com.hahn.userDataService.models.UnregisteredUserView;
import com.hahn.userDataService.models.UserView;
import com.hahn.userDataService.utils.UserDataUtil;
import com.hahn.userDataService.utils.UserRestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/all")
    public List<UserView> getAllUsers() {

        List<RegisteredUserView> registeredUserViews = UserRestUtil.getUserData(UserRestUtil.REGISTERED_USERS_URL, restTemplate);
        List<UnregisteredUserView> unregisteredUserViews = UserRestUtil.getUserData(UserRestUtil.UNREGISTERED_USERS_URL, restTemplate);
        List<ProjectMembershipView> projectMembershipsView = UserRestUtil.getUserData(UserRestUtil.PROJECT_MEMBERSHIPS_URL, restTemplate);

        ObjectMapper mapper = new ObjectMapper();

        // Due to the getUserData returning a generically typed list, needed to convert value to provide type information that was lost
        return UserDataUtil.createUserViews(
                mapper.convertValue(registeredUserViews, new TypeReference<>() {
                }),
                mapper.convertValue(unregisteredUserViews, new TypeReference<>() {
                }),
                mapper.convertValue(projectMembershipsView, new TypeReference<>() {
                })
        );
    }
}