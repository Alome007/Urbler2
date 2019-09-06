package com.urbler.main;

public class recPojo {
    public recPojo(){
        //for firebase
    }
    String name;

    public recPojo(String name, String country, String state, String avatarUrl, String accountType) {
        this.name = name;
        this.country = country;
        this.state = state;
        this.avatarUrl = avatarUrl;
        this.accountType = accountType;
    }

    String country;
    String state;
    String avatarUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    String accountType;
}
