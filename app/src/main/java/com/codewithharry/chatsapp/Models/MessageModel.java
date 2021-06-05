package com.codewithharry.chatsapp.Models;

public class MessageModel {

    String uId, messageId, groupmessageid,username ;
    String message ;
    Long timestamp;

    public MessageModel(String uId, String message, Long timestamp) {
        this.uId = uId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public MessageModel(String uId, String message) {
        this.uId = uId;
        this.message = message;
    }

//    public MessageModel(String uId, String message, String username) {
//        this.uId = uId;
//        this.message = message;
//        this.username = username;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroupmessageid() {
        return groupmessageid;
    }

    public void setGroupmessageid(String groupmessageid) {
        this.groupmessageid = groupmessageid;
    }



    public  MessageModel(){};

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
