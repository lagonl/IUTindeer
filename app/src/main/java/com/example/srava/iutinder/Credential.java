package com.example.srava.iutinder;

/**
 * Created by lagonl on 01/03/2016.
 */
public class Credential {

    public String userName;
    public String password;

    public Credential() {
    }

    public Credential(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    public String getUsername() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "username='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
