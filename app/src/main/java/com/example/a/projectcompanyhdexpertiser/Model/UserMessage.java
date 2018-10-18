package com.example.a.projectcompanyhdexpertiser.Model;

import java.io.Serializable;

public class UserMessage implements Serializable {

    private String id;
    private String Name;
    private String Message;
    private String Phone;
    private String Active;

    public UserMessage(String id, String userName, String UserMessage, String UserPhone, String inactive) {
        this.id = id;
        this.Name = userName;
        this.Message = UserMessage;
        this.Phone = UserPhone;
        this.Active = inactive;
    }

    public UserMessage(String id, String userName, String UserMessage, String UserPhone, boolean active, String inactive) {
        this.id = id;
        this.Name = userName;
        this.Message = UserMessage;
        this.Phone = UserPhone;
        this.Active = inactive;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInActive() {
        return Active;
    }

    public void setInActive(String inActive) {
        this.Active = inActive;
    }

    private boolean active;


    public String getUserMessage() {
        return Message;
    }

    public void setUserMessage(String UserMessage) {
        this.Message = UserMessage;
    }

    public String getUserName() {
        return Name;
    }

    public void setUserName(String userName) {
        this.Name = userName;
    }


    public String getUserPhone() {
        return Phone;
    }

    public void setUserPhone(String userPhone) {
        this.Phone = userPhone;
    }

    @Override
    public String toString() {
        return this.Name + " (" + this.Phone + ")" + "\n" +
                this.Message + "\n" +
                this.Active;

    }

}
