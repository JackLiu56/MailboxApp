package com.njit.android.emailmobileterminal;

import android.app.Application;
import android.icu.util.Measure;

import javax.mail.Message;

public class MyApplication extends Application {
    private FetchEmail fetchEmail;
    private String userName;
    private String password;
    private Message message;
    public FetchEmail getFetchEmail() {
        return fetchEmail;
    }

    public void setFetchEmail(FetchEmail fetchEmail) {
        this.fetchEmail = fetchEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

}
