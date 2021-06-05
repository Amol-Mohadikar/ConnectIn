package com.codewithharry.chatsapp.Models;

public class Users {

    String userName;
    String password;
    String mail;
    String userId;
    String lastMessage;
    String profilepic;
    String status;






    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

//    public Users(String userName , String password , String mail , String  userId , String  lastMessage , String  profilepic) {
//        this.userName = userName;
//        this.password = password;
//        this.mail = mail;
//        this.userId = userId;
//        this.lastMessage = lastMessage;
//        this.profilepic = profilepic;

    public Users(String userName, String password, String mail, String userId, String lastMessage, String profilepic, String status ) {
        this.userName = userName;
        this.password = password;
        this.mail = mail;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.profilepic = profilepic;
        this.status = status;

    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
//    }

    public Users (){}

    //    sign up constuctor

    public Users( String password  , String userName , String mail) {
        this.userName = userName;
        this.password = password;
        this.mail = mail;

    }




//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getMail() {
//        return mail;
//    }
//
//    public void setMail(String mail) {
//        this.mail = mail;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//
//
//    public String getLastMessage() {
//        return lastMessage;
//    }
//
//    public void setLastMessage(String lastMessage) {
//        this.lastMessage = lastMessage;
//    }
//
//
//
//    public String getProfilepic() {
//        return profilepic;
//    }
//
//    public void setProfilepic(String profilepic) {
//        this.profilepic = profilepic;
//    }


}
