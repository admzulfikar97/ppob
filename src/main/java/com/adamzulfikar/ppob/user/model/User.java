package com.adamzulfikar.ppob.user.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
//@Data
public class User {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String filename;
    private String filepath;
    private String createdAt;

    public User() {}

    public User(Long id, String firstname, String lastname, String email, String password, String filename, String filepath, String createdAt) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.filename = filename;
        this.filepath = filepath;
        this.createdAt = createdAt;
    }

    public User(String filename, String filepath) {
        this.filename = filename;
        this.filepath = filepath;
    }

//    public Long getId() {
//        return id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
//    public String getFirstname() {
//        return firstname;
//    }
//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }
//    public String getLastname() {
//        return lastname;
//    }
//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }
//    public String getEmail() {
//        return email;
//    }
//    public void setEmail(String email) {
//        this.email = email;
//    }
//    public String getPassword() {
//        return password;
//    }
//    public void setPassword(String password) {
//        this.password = password;
//    }
//    public String getCreatedAt() {
//        return createdAt;
//    }
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }
}
