package com.urbler.main;

public class appPojo {
    public appPojo(){
        //for firebase
    }
    String city;
    String phoneNumber;
    String type;
    int position;
    double qty;
    String address;
    String hubName;
    String description;

    public String getHubName() {
        return hubName;
    }

    public void setHubName(String hubName) {
        this.hubName = hubName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    String pushId;
    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    String day;
    String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    int status;
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public appPojo(String city, String phoneNumber, String type, double qty, int position, String day, String location,int status, String pushId, String name, String address, String hubName,String description) {
        this.city = city;
        this.name=name;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.qty = qty;
        this.position = position;
        this.day=day;
        this.status=status;
        this.location=location;
        this.pushId=pushId;
        this.address=address;
        this.hubName=hubName;
        this.description=description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
