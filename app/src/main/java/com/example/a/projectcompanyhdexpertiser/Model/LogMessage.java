package com.example.a.projectcompanyhdexpertiser.Model;

import java.io.Serializable;

public class LogMessage implements Serializable {
    private String log;

    public LogMessage(String log) {
        this.log = log;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return log;
    }
}

