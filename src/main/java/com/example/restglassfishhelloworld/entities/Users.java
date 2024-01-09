package com.example.restglassfishhelloworld.entities;

public class Users {
    private Integer id;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userFname;
    private String userLname;
    private String userPassword;
    private Integer userCityId;
    private String userAdress;

    private Integer userLoginStatus;

    public Users() {
    }

    public Users(Integer id, String userName, String userEmail, String userPhone, String userFname, String userLname, String userPassword, Integer userCityId, String userAdress, Integer userLoginStatus) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userFname = userFname;
        this.userLname = userLname;
        this.userPassword = userPassword;
        this.userCityId = userCityId;
        this.userAdress = userAdress;
        this.userLoginStatus = userLoginStatus;
    }

    public Integer getId() {
        return id;
    }



    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserFname() {
        return userFname;
    }

    public String getUserLname() {
        return userLname;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public Integer getUserCityId() {
        return userCityId;
    }

    public String getUserAdress() {
        return userAdress;
    }

    public Integer getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserCityId(Integer userCityId) {
        this.userCityId = userCityId;
    }

    public void setUserAdress(String userAdress) {
        this.userAdress = userAdress;
    }

    public void setUserLoginStatus(Integer userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }
}
