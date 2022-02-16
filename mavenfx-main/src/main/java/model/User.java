package model;

import java.io.Serializable;

public class User implements Serializable {
    private final String username;
    private final String password;
    private final UserType usertype;

    public enum UserType {
        Admin, User
    }

    public User(String username, String password, UserType usertype) {
        this.username = username;
        this.password = password;
        this.usertype = usertype;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUsertype() {
        return usertype;
    }
}