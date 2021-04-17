package com.example.bdosn_app;

public class MissingPerson {
    String age,contact,desc,gender,height,image,last_seen,location,name,relation;

    public MissingPerson() {
    }

    public MissingPerson(String age, String contact, String desc, String gender, String height, String image, String last_seen, String location, String name, String relation) {
        this.age = age;
        this.contact = contact;
        this.desc = desc;
        this.gender = gender;
        this.height = height;
        this.image = image;
        this.last_seen = last_seen;
        this.location = location;
        this.name = name;
        this.relation = relation;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
