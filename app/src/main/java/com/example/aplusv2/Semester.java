package com.example.aplusv2;

import java.util.ArrayList;

public class Semester {
    private String user_id;
    private String sem_id;
    private float sem_gpa;

    public Semester(String user_id, String sem_id) {
        this.user_id = user_id;
        this.sem_id = sem_id;
        sem_gpa = 0;
    }
    public Semester(String user_id, String sem_id, float sem_gpa) {
        this.user_id = user_id;
        this.sem_id = sem_id;
        this.sem_gpa = sem_gpa;
    }

    public String getUserID(){ return this.user_id; }
    public String getSemesterID(){ return this.sem_id; }
    public Float getSemesterGPA(){ return this.sem_gpa; }
}
