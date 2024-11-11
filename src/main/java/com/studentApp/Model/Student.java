package com.studentApp.Model;

public class Student {
    private String id;
    private String firstName;
    private String lastName;
    private String gender;
    private double gpa;
    private int level;
    private String address;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public double getGpa() {
        return gpa;
    }

    public int getLevel() {
        return level;
    }

    public String getAddress() {
        return address;
    }

    public Student(String id, String firstName, String lastName, String gender, double gpa, int level, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.gpa = gpa;
        this.level = level;
        this.address = address;
    }

    public String getStudentID() {
        return this.id;
    }
}
