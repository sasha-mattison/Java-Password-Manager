package org.example;

public class User {

    private String account, username, password;

    public User(String account, String username, String password) {
        this.account = account;
        this.username = username;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String formatUserData() {
        return "{" + this.account + "," + this.username + "." + this.password + "}";

    }


    
    
}
