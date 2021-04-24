package com.example.bdosn_app_rescue;

public class CreateUser {
    private String name;
    private String age;
    private String email;
    private String phone;
    private String password;
    private String em1;
    private String em2;
    private String em3;
    private int code;
    private boolean isSharing;
    private double Latitude;
    private double Longitude;
    private String userId;

    public CreateUser(String name, String age, String email, String phone, String password, String em1, String em2, String em3,  int code, boolean isSharing, double latitude, double longitude,String userId) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.em1 = em1;
        this.em2 = em2;
        this.em3 = em3;
        this.code = code;
        this.isSharing = isSharing;
        Latitude = latitude;
        Longitude = longitude;
        this.userId=userId;
    }

    //Getter

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getEm1() {
        return em1;
    }

    public String getEm2() {
        return em2;
    }

    public String getEm3() {
        return em3;
    }


    public int getCode() {
        return code;
    }

    public boolean isSharing() {
        return isSharing;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }
    //Setter

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEm1(String em1) {
        this.em1 = em1;
    }

    public void setEm2(String em2) {
        this.em2 = em2;
    }

    public void setEm3(String em3) {
        this.em3 = em3;
    }


    public void setCode(int code) {
        this.code = code;
    }

    public void setSharing(boolean sharing) {
        isSharing = sharing;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

}
