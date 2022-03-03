package com.hahn.userDataService.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserView {

    public String id;
    public String city;
    public String company;
    public String country;
    public String firstName;
    public String lastName;
    public String organizationType;
    public String phone;
    public String state;
    public String zipCode;
    public Boolean disclaimerAccepted;
    public String languageCode;
    public String emailAddress;
    public String registrationId;
    public String registrationIdGeneratedTime;
    public Set<String> projectIds;
}
