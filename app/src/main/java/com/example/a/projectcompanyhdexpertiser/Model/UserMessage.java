package com.example.a.projectcompanyhdexpertiser.Model;

import java.io.Serializable;

public class UserMessage implements Serializable {

    private String id;
    private String userName;
    private String userMessage;
    private String userPhone;
    private String inActive;

    public UserMessage(String id, String userName, String UserMessage, String UserPhone, String inactive) {
        this.id = id;
        this.userName = userName;
        this.userMessage = UserMessage;
        this.userPhone = UserPhone;
        this.inActive = inactive;
    }

    public UserMessage(String id, String userName, String UserMessage, String UserPhone, boolean active, String inactive) {
        this.id = id;
        this.userName = userName;
        this.userMessage = UserMessage;
        this.userPhone = UserPhone;
        this.active = active;
        this.inActive = inactive;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInActive() {
        return inActive;
    }

    public void setInActive(String inActive) {
        this.inActive = inActive;
    }

    private boolean active;


    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String UserMessage) {
        this.userMessage = UserMessage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public String toString() {
        return this.userName + " (" + this.userPhone + ")" + "\n" +
                this.userMessage + "\n" +
                this.inActive;

    }

}
