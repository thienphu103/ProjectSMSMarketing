package com.example.a.projectcompanyhdexpertiser.APIConnect;

public class APIConnection {
    public static String LINK="http://192.168.1.53:8181/api/8.0/system/";
    public static String HOST="http://192.168.1.53:8181/";
    public static String PATH="api/8.0/system/";

    public static String getHOST() {
        return HOST;
    }

    public static void setHOST(String HOST) {
        APIConnection.HOST = HOST;
    }

    public static String getPATH() {
        return PATH;
    }

    public static void setPATH(String PATH) {
        APIConnection.PATH = PATH;
    }
}
