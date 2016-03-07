package com.example.srava.iutinder;

/**
 * Created by lagonl on 01/03/2016.
 */
public class Credential {

    public String username;
    public String password;

    public Credential() {
    }

    public Credential(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
