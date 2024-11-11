package com.studentApp.Model;

import java.util.ArrayList;
import java.util.List;

public class University {
    private List<Student> students;

    public University() {
        this.students = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public boolean removeStudent(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                students.remove(student);
                return true; // Student found and removed
            }
        }
        return false; // Student not found
    }

    public List<Student> findStudentsByFirstName(String firstName) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getFirstName().equalsIgnoreCase(firstName)) {
                result.add(student);
            }
        }
        return result;
    }

    public List<Student> findStudentsByGpa(double gpa) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getGpa() == gpa) {
                result.add(student);
            }
        }
        return result;
    }

}
