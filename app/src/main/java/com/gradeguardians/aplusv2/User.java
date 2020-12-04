package com.gradeguardians.aplusv2;

public class User {
    private String user_id;
    private String pass;
    private double cumGPA;

    public User(String user_id, String pass) {
        this.user_id = user_id;
        this.pass = pass;
        cumGPA = 0.0;
    }
    public User(String user_id, String pass, double cumGPA) {
        this.user_id = user_id;
        this.pass = pass;
        this.cumGPA = cumGPA;
    }

    public boolean authUser(String pass) {
        if(pass == pass){
            return true;
        }
        else{
            return false;
        }
    }

    public String getUserID(){ return user_id; }
    public String getPass(){ return pass; }
    public String getGPA(){ return String.format("%.2f", cumGPA);}
}

