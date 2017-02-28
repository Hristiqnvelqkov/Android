package com.apress.gerber.stolencars;

/**
 * Created by hriso on 2/28/2017.
 */
public class UserModel {
    private int id;
    String email;
    String pass;
    public UserModel(int id, String email, String pass){
        this.id=id;
        this.email=email;
        this.pass=pass;
    }
    int getId(){
        return this.id;
    }
    String getEmail(){
        return this.email;
    }
}
