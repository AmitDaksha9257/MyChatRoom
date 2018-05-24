package com.kil.mychatroom.Model;

public class User {
    public String email;
    public String password;
    public String emailhint;
    public String passwordhint;
    public String uid;
    public String fcm_token;

    public User(String uid,String email,String token){
        this.uid=uid;
        this.email=email;
        this.fcm_token=token;
    }

    public User(String emailHint,String passwordhint){
        this.emailhint=emailHint;
        this.passwordhint=passwordhint;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }


}
