package com.example.bdosn_app;

class Userclass {

    private String name;
    private int age;
    private String email;
    private String phone;
    private String password;
    private String em1;
    private String em2;
    private String em3;
    private String image;
    private int code;
    private boolean isSharing;
    private String Latitude;
    private String Logitude;

    public Userclass(String name, int age, String email, String phone, String password, String em1, String em2, String em3, String image, int code, boolean isSharing, String latitude, String logitude) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.em1 = em1;
        this.em2 = em2;
        this.em3 = em3;
        this.image = image;
        this.code = code;
        this.isSharing = isSharing;
        Latitude = latitude;
        Logitude = logitude;
    }

    //Getter

    public String getName() {
        return name;
    }

    public int getAge() {
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

    public String getImage() {
        return image;
    }

    public int getCode() {
        return code;
    }

    public boolean isSharing() {
        return isSharing;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLogitude() {
        return Logitude;
    }
    //Setter

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
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

    public void setImage(String image) {
        this.image = image;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setSharing(boolean sharing) {
        isSharing = sharing;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public void setLogitude(String logitude) {
        Logitude = logitude;
    }
}
