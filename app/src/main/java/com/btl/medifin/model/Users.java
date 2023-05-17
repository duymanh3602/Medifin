package com.btl.medifin.model;

public class Users {
    private String userName, fullName, password, email, level, age, birthday, phone, description, specialized, docAdd, docInfo, gender;

    public Users() {
    }

    public Users(String userName, String fullName, String password, String email, String level, String age, String birthday, String phone, String description, String specialized, String doctorAdd, String doctorInfo) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.level = level;
        this.age = age;
        this.birthday = birthday;
        this.phone = phone;
        this.description = description;
        this.specialized = specialized;
        this.docAdd = doctorAdd;
        this.docInfo = doctorInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecialized() {
        return specialized;
    }

    public void setSpecialized(String specialized) {
        this.specialized = specialized;
    }

    public String getDocAdd() {
        return docAdd;
    }

    public void setDoctorAdd(String doctorAdd) {
        this.docAdd = doctorAdd;
    }

    public String getDocInfo() {
        return docInfo;
    }

    public void setDoctorInfo(String doctorInfo) {
        this.docInfo = doctorInfo;
    }

    public void setDocAdd(String docAdd) {
        this.docAdd = docAdd;
    }

    public void setDocInfo(String docInfo) {
        this.docInfo = docInfo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
