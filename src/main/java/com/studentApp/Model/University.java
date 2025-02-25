package com.studentApp.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

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
                return true;
            }
        }
        return false; // Student not found
    }

    public List<Student> findStudentsByStudentID(String studentID) {
        return students.stream()
                .filter(student -> student.getId().trim().equalsIgnoreCase(studentID.trim()))
                .collect(Collectors.toList());
    }

    public List<Student> findStudentsByFirstName(String firstName) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getFirstName().equalsIgnoreCase(firstName.toLowerCase())) {
                result.add(student);
            }
        }
        return result;
    }

    public List<Student> findStudentsByLastName(String lastName) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getLastName().equalsIgnoreCase(lastName.toLowerCase())) {
                result.add(student);
            }
        }
        return result;
    }


    public List<Student> findStudentsByGender(String gender) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getGender().equalsIgnoreCase(gender)) {
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
    public List<Student> findStudentsByLevel(int level) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getLevel() == level) {
                result.add(student);
            }
        }
        return result;
    }


    public List<Student> findStudentsByAddress(String address) {
        List<Student> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(address, Pattern.CASE_INSENSITIVE);  // Compiling the regex pattern with case-insensitivity
        for (Student student : students) {
            String studentAddress = student.getAddress();
            Matcher matcher = pattern.matcher(studentAddress);

            if (matcher.find()) {  // If the regex matches any part of the address
                result.add(student);
            }
        }
        return result;
    }

    public boolean isDuplicateID(String studentID) {
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)) {
                return true; // Duplicate ID found
            }
        }
        return false; // No duplicate found
    }

    public boolean isValidString(String name) {
        // Regex for alphabetic characters with spaces, no leading or trailing spaces
        return name != null && name.matches("[a-zA-Z]+(\\s[a-zA-Z]+)*");
    }


    public boolean isValidGpa(String gpaString) {
        try {
            double gpa = Double.parseDouble(gpaString);
            return gpa >= 0.0 && gpa <= 4.0; // GPA should be between 0.0 and 4.0
        } catch (NumberFormatException e) {
            return false; // Return false if GPA is not a valid number
        }
    }


    public boolean isValidStudentID(String studentIDStr) {
        if (studentIDStr != null && !studentIDStr.trim().isEmpty()) {
            return studentIDStr.matches("\\d+"); // Regular expression to match only digits
        }
        return false; // Null or empty string is invalid
    }

    public List<String> validateStudent(String studentID, String firstName, String lastName, String gender,
                                        String gpaString, String levelString, String address, boolean isUpdate) {
        List<String> errorMessages = new ArrayList<>();

        // Validate Student ID
        if (!isValidStudentID(studentID)) {
            errorMessages.add("Invalid Student ID.");
        }

        // Check for duplicates if it's not an update
        if (!isUpdate && isDuplicateID(studentID)) {
            errorMessages.add("Duplicate Student ID: " + studentID);
        }

        // Validate first name
        if (!isValidString(firstName)) {
            errorMessages.add("Invalid First Name.");
        }

        // Validate last name
        if (!isValidString(lastName)) {
            errorMessages.add("Invalid Last Name.");
        }

        // Validate address
        if (!isValidString(address)) {
            errorMessages.add("Invalid Address.");
        }

        // Validate GPA
        if (!isValidGpa(gpaString)) {
            errorMessages.add("Invalid GPA.");
        }

        // Validate Level
        try {
            int level = Integer.parseInt(levelString);
            if (level < 1 || level > 4) { // Example range for levels
                errorMessages.add("Level must be between 1 and 4.");
            }
        } catch (NumberFormatException e) {
            errorMessages.add("Invalid Level: must be a number.");
        }

        return errorMessages;
    }


}
