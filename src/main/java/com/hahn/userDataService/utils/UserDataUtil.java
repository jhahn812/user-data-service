package com.hahn.userDataService.utils;

import com.hahn.userDataService.models.ProjectMembershipView;
import com.hahn.userDataService.models.RegisteredUserView;
import com.hahn.userDataService.models.UnregisteredUserView;
import com.hahn.userDataService.models.UserView;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class UserDataUtil {

    public static List<UserView> createUserViews(List<RegisteredUserView> registeredUserViews,
                                                 List<UnregisteredUserView> unregisteredUserViews,
                                                 List<ProjectMembershipView> projectMembershipViews) {

        Map<String, Set<String>> projectMembershipMap = buildProjectMembershipMap(projectMembershipViews);

        // Create generic users from registered users
        List<UserView> users = registeredUserViews.stream()
                .map(v -> createUserView(v, projectMembershipMap))
                .collect(Collectors.toList());

        // Create generic users from un-registered users
        users.addAll(unregisteredUserViews.stream()
                .map(v -> createUserView(v, projectMembershipMap))
                .collect(Collectors.toList()));

        return users;
    }

    private static Map<String, Set<String>> buildProjectMembershipMap(List<ProjectMembershipView> projectMembershipViews) {

        Map<String, Set<String>> projectMembershipMap = new HashMap<>();

        projectMembershipViews.stream()
                .filter(v -> StringUtils.hasText(v.userId))
                .forEach(v -> projectMembershipMap.computeIfAbsent(v.userId, k -> new HashSet<>()).add(v.projectId));

        return projectMembershipMap;
    }

    private static UserView createUserView(RegisteredUserView registeredUserView,
                                           Map<String, Set<String>> projectMembershipMap) {

        UserView userView = new UserView();
        setCommonUserViewFields(userView, registeredUserView.id, registeredUserView.emailAddress, registeredUserView.languageCode, projectMembershipMap);
        userView.city = registeredUserView.city;
        userView.company = registeredUserView.company;
        userView.country = registeredUserView.country;
        userView.firstName = registeredUserView.firstName;
        userView.lastName = registeredUserView.lastName;
        userView.organizationType = registeredUserView.organizationType;
        userView.phone = registeredUserView.phone;
        userView.state = registeredUserView.state;
        userView.zipCode = registeredUserView.zipCode;
        userView.disclaimerAccepted = registeredUserView.disclaimerAccepted;

        return userView;
    }

    private static UserView createUserView(UnregisteredUserView unregisteredUserView,
                                           Map<String, Set<String>> projectMembershipMap) {

        UserView userView = new UserView();
        setCommonUserViewFields(userView, unregisteredUserView.id, unregisteredUserView.emailAddress, unregisteredUserView.languageCode, projectMembershipMap);
        userView.registrationId = unregisteredUserView.registrationId;
        userView.registrationIdGeneratedTime = unregisteredUserView.registrationIdGeneratedTime;

        return userView;
    }

    private static void setCommonUserViewFields(UserView userView,
                                                String id,
                                                String emailAddress,
                                                String languageCode,
                                                Map<String, Set<String>> projectMembershipMap) {
        userView.id = id;
        userView.emailAddress = emailAddress;
        userView.languageCode = languageCode;
        userView.projectIds = projectMembershipMap.get(id) != null ? projectMembershipMap.get(id) : Collections.emptySet();
    }
}
