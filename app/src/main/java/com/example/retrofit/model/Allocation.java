package com.example.retrofit.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.DayOfWeek;

@Entity
public class Allocation implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "server_id")
    private int id;
    private String dayOfWeek;
    private int startHour;
    private int endHour;
    private Professor professor;
    private Course course;

    public Allocation() {
    }

    public Allocation(String dayOfWeek, int startHour, int endHour, Professor professor, Course course) {
        this.dayOfWeek = dayOfWeek;
        this.startHour = startHour;
        this.endHour = endHour;
        this.professor = professor;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

//    @Override
//    public String toString() {
//        return dayOfWeek + " às " + startHour + "-" + endHour + " / " + professor + " / " + course;
//    }
}
