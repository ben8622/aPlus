package com.example.aplusv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    /*  Our names for tables, some names will be reused in other tables (i.e. USER_ID) */
    public static final String USER_TABLE = "USERS";
    public static final String SEM_TABLE = "SEMESTERS";
    public static final String COURSE_TABLE = "COURSES";

    /* names for the columns in USERS */
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USER_ID = "USER_ID";
    public static final String COLUMN_PASS = "PASS";
    public static final String COLUMN_CUMGPA = "CUMGPA";

    /* names for the columns in SEMESTERS table */
    /* will also track what user it belongs to with USER_ID */
    public static final String COLUMN_SEM_ID = "SEM_ID";
    public static final String COLUMN_SEM_GPA = "SEM_GPA";

    /* names for the columns in COURSES table */
    /* will also track what user it belongs to with USER_ID */
    /* will also track what semester it belongs to with SEM_ID */
    public static final String COLUMN_COURSE_ID = "COURSE_ID";
    public static final String COLUMN_COURSE_WEIGHT = "COURSE_WEIGHT";
    public static final String COLUMN_COURSE_GRADE = "COURSE_GRADE";
    public static final String COLUMN_LETTER_GRADE = "LETTER_GRADE";




    public DatabaseHelper(@Nullable Context context) {
        super(context, "users.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* sql code to create the database */
        String user_tbl_string = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + "(" +
                COLUMN_USER_ID + " TEXT UNIQUE, " +
                COLUMN_PASS + " TEXT, " +
                COLUMN_CUMGPA + " NUMERIC )";
        String semester_tbl_string = "CREATE TABLE IF NOT EXISTS " + SEM_TABLE + "(" +
                COLUMN_USER_ID + " TEXT, " +
                COLUMN_SEM_ID + " TEXT, " +
                COLUMN_SEM_GPA + " NUMERIC )";
        String course_tbl_string = "CREATE TABLE IF NOT EXISTS " + COURSE_TABLE + "(" +
                COLUMN_USER_ID + " TEXT, " +
                COLUMN_SEM_ID + " TEXT, " +
                COLUMN_COURSE_ID + " TEXT, " +
                COLUMN_COURSE_WEIGHT + " INTEGER, " +
                COLUMN_COURSE_GRADE + " NUMERIC, " +
                COLUMN_LETTER_GRADE + " TEXT )";

        /* combining all sql strings seperated by ';' */
        String all_tbls = user_tbl_string + "; " +
                semester_tbl_string + "; " +
                course_tbl_string + ";";

        db.execSQL(user_tbl_string);
        db.execSQL(semester_tbl_string);
        db.execSQL(course_tbl_string);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + i + " to "
                        + i1 + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS table1");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS table2");

        onCreate(sqLiteDatabase);
    }

    /* functions to add single element to database */
    public boolean addUser(User user) {

        // Opens database (that we hardcoded) as writeable which locks anyone else tampering with
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_ID, user.getUserID());
        cv.put(COLUMN_PASS, user.getPass());
        cv.put(COLUMN_CUMGPA, 0.0);

        // insert = -1 if fails
        long insert = db.insert(USER_TABLE, null, cv);
        db.close();
        if(insert == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean addSemester(Semester semester) {

        // Opens database (that we hardcoded) as writeable which locks anyone else tampering with
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_ID, semester.getUserID());
        cv.put(COLUMN_SEM_ID, semester.getSemesterID());
        cv.put(COLUMN_SEM_GPA, 0.0);

        // insert = -1 if fails
        long insert = db.insert(SEM_TABLE, null, cv);
        db.close();
        if(insert == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean addCourse(Course course) {

        // Opens database (that we hardcoded) as writeable which locks anyone else tampering with
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_ID, course.getUserID());
        cv.put(COLUMN_SEM_ID, course.getSemesterID());
        cv.put(COLUMN_COURSE_ID, course.getCourseID());
        cv.put(COLUMN_COURSE_WEIGHT, course.getCourseWeight());
        cv.put(COLUMN_COURSE_GRADE, course.getCourseGrade());
        cv.put(COLUMN_LETTER_GRADE, course.getCourseLetterGrade());

        // insert = -1 if fails
        long insert = db.insert(COURSE_TABLE, null, cv);
        db.close();
        if(insert == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean deleteUser(User user){
        boolean ret = false;

        SQLiteDatabase db = this.getWritableDatabase();
        String query_string = "DELETE FROM " + USER_TABLE + " WHERE " + COLUMN_USER_ID + " = " + user.getUserID();

        Cursor cursor = db.rawQuery(query_string, null);

        if(cursor.moveToFirst()) {
            return true;
        }
        else{
            return false;
        }
    }
    public boolean deleteSemester(Semester sem){
        boolean ret = false;

        SQLiteDatabase db = this.getWritableDatabase();
        String query_string = "DELETE FROM " + SEM_TABLE +
                " WHERE " + COLUMN_USER_ID + " = " + sem.getUserID() +
                " AND " + COLUMN_SEM_ID + " = " + sem.getSemesterID();

        Cursor cursor = db.rawQuery(query_string, null);

        if(cursor.moveToFirst()) {
            return true;
        }
        else{
            return false;
        }
    }
    public boolean deleteCourse(Course course){
        boolean ret = false;

        SQLiteDatabase db = this.getWritableDatabase();
        String query_string = "DELETE FROM " + SEM_TABLE +
                " WHERE " + COLUMN_USER_ID + " = " + course.getUserID() +
                " AND " + COLUMN_SEM_ID + " = " + course.getSemesterID() +
                " AND " +   COLUMN_COURSE_ID + " = " + course.getCourseID();

        Cursor cursor = db.rawQuery(query_string, null);

        if(cursor.moveToFirst()) {
            return true;
        }
        else{
            return false;
        }
    }

    public List<User> getAllUsers(){
        List<User> all_users = new ArrayList<>();

        String query_string ="SELECT * FROM " + USER_TABLE;

        SQLiteDatabase db =this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query_string, null);

        if(cursor.moveToFirst()){
            do{
                String username = cursor.getString(0);
                String password = cursor.getString(1);

                User tmp_user = new User(username, password);

                all_users.add(tmp_user);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return all_users;
    }

}
