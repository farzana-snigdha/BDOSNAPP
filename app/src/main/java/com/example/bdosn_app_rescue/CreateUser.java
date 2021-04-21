package com.example.bdosn_app_rescue;

public class CreateUser {
    private String name,latitude,longitude,age,phone,email,password,em1,em2,em3,image,code,isSharing,userId;

    public CreateUser(String name, String latitude, String longitude, String age, String phone, String email, String password, String em1, String em2, String em3, String image, String code, String isSharing,String userId) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.em1 = em1;
        this.em2 = em2;
        this.em3 = em3;
        this.image = image;
        this.code = code;
        this.isSharing = isSharing;
        this.userId=userId;
    }


    public CreateUser() {
    }
    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEm1() {
        return em1;
    }

    public void setEm1(String em1) {
        this.em1 = em1;
    }

    public String getEm2() {
        return em2;
    }

    public void setEm2(String em2) {
        this.em2 = em2;
    }

    public String getEm3() {
        return em3;
    }

    public void setEm3(String em3) {
        this.em3 = em3;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIsSharing() {
        return isSharing;
    }

    public void setIsSharing(String isSharing) {
        this.isSharing = isSharing;
    }
}
