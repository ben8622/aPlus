package com.example.aplusv2;

public class Course {
    private String user_id;
    private String sem_id;
    private String course_id;
    private int weight;
    private double grade;
    private String letter_grade;

    public Course(String user_id, String sem_id, String course_id, int weight, double grade, String letter_grade) {
        this.user_id = user_id;
        this.sem_id = sem_id;
        this.course_id = course_id;
        this.weight = weight;
        this.grade = grade;
        this.letter_grade = letter_grade;
    }

    public String getUserID(){ return user_id; }
    public String getSemesterID(){ return sem_id; }
    public String getCourseID(){ return course_id; }
    public int getCourseWeight(){ return weight; }
    public double getCourseGrade(){ return grade; }
    public String getCourseLetterGrade(){ return letter_grade; }
}
