package com.rest.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(value = "")
@JsonInclude(JsonInclude.Include.NON_NULL) // Not sending the null values only.. For the empty HASHMAP we will use EMPTY
public class User {
    private String name;
    private String job;
//    @JsonIgnore -> we can also use this
    private String createdAt;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT) // Not sending the default value for ID, We can set it at class level
    private int id;

    public User() {

    }

    public User(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
