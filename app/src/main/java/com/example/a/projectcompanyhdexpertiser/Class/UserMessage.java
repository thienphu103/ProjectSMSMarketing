package com.example.a.projectcompanyhdexpertiser.Class;

import java.io.Serializable;

public class UserMessage implements Serializable {

    private String userName;
    private String userMessage;
    private String userPhone;

    private boolean active;

    public UserMessage(String userName, String UserMessage,String UserPhone)  {
        this.userName= userName;
        this.userMessage = UserMessage;
        this.userPhone=UserPhone;
        this.active= true;
    }

    public UserMessage(String userName, String UserMessage,String UserPhone, boolean active)  {
        this.userName= userName;
        this.userMessage = UserMessage;
        this.userPhone=UserPhone;
        this.active= active;
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public String toString() {
        return this.userName +" ("+ this.userPhone+")"+"\n"+
                this.userMessage+"\n";

    }

}
