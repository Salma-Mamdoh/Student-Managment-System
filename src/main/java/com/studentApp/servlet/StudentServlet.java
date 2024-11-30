package com.studentApp.servlet;

import com.studentApp.Model.Student;
import com.studentApp.Model.University;
import com.studentApp.util.XMLUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StudentServlet extends HttpServlet {
    private static final String XML_FILE_PATH = "C:\\Users\\E.J.S\\IdeaProjects\\SOA-Assignment\\src\\main\\resources\\students.xml";
    private University university;

    @Override
    public void init() throws ServletException {
        try {
            File xmlFile = new File(XML_FILE_PATH);
            if (!xmlFile.exists()) {
                XMLUtil.createNewXMLFile(XML_FILE_PATH);
            }
            university = XMLUtil.readStudentsFromXML(XML_FILE_PATH);
        } catch (Exception e) {
            throw new ServletException("Error reading student data from XML.", e);
        }
    }

    // Handle HTTP POST requests for adding, searching, and deleting students
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("addStudents".equals(action)) {
            try {
                addStudents(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("search".equals(action)) {
            String searchType = request.getParameter("searchType");
            if ("firstName".equals(searchType)) {
                searchStudentByFirstName(request, response);
            } else if ("gpa".equals(searchType)) {
                searchStudentByGpa(request, response);
            } else if ("studentID".equals(searchType)) {
                searchStudentByStudentID(request, response);
            } else if ("lastName".equals(searchType)) {
                searchStudentByLastName(request, response);
            } else if ("level".equals(searchType)) {
                searchStudentByLevel(request, response);
            } else if ("gender".equals(searchType)) {
                searchStudentByGender(request, response);
            } else if ("address".equals(searchType)) {
                searchStudentByAddress(request, response);
            }
        } else if ("delete".equals(action)) {
            deleteStudent(request, response);
        } else if ("update".equals(action)) {
            List<Student> students = university.getStudents();

            request.setAttribute("students", students);

            RequestDispatcher dispatcher = request.getRequestDispatcher("updateStudent.jsp");
            dispatcher.forward(request, response);
        } else if ("updateStudent".equals(action)) {
            try {
                updateStudent(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sort".equals(action)) {
            String sortBy = request.getParameter("sortBy");
            String sortOrder = request.getParameter("sortOrder");

            // Fetch the students list from the university (or other data source)
            List<Student> students = university.getStudents();

            // If the list is empty, set a message and forward to JSP without sorting
            if (students == null || students.isEmpty()) {
                request.setAttribute("noData", "No student data available to display.");
                request.getRequestDispatcher("/sortStudent.jsp").forward(request, response);
                return;
            }

            // Call the appropriate sorting function based on user input
            switch (sortBy) {
                case "ID":
                    sortByID(students, sortOrder);
                    break;
                case "FirstName":
                    sortByFirstName(students, sortOrder);
                    break;
                case "GPA":
                    sortByGPA(students, sortOrder);
                    break;
                case "Level":
                    sortByLevel(students, sortOrder);
                    break;
                default:
                    // Handle invalid sorting attribute (optional)
                    break;
            }

            // Save the sorted list back to the file
            try {
                XMLUtil.saveStudentsToXML(XML_FILE_PATH, university);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Forward the sorted students list to the JSP for display
            request.setAttribute("students", students);
            request.getRequestDispatcher("/sortStudent.jsp").forward(request, response);
        }

    }


    private void addStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int numStudents = Integer.parseInt(request.getParameter("numStudents"));
        List<String> errorMessages = new ArrayList<>();

        for (int i = 1; i <= numStudents; i++) {
            String studentID = request.getParameter("studentID" + i).trim();
            String firstName = request.getParameter("firstName" + i).trim();
            String lastName = request.getParameter("lastName" + i).trim();
            String gender = request.getParameter("gender" + i).trim();
            String gpaString = request.getParameter("gpa" + i).trim();
            String levelString = request.getParameter("level" + i).trim();
            String address = request.getParameter("address" + i).trim();

            // Validate inputs
            errorMessages.addAll(university.validateStudent(studentID, firstName, lastName, gender, gpaString, levelString, address, false));

            if (!errorMessages.isEmpty()) {
                break; // Stop processing if there's any error
            }

            // Create new Student object after validations pass
            double gpa = Double.parseDouble(gpaString);
            int level = Integer.parseInt(levelString);

            Student student = new Student(studentID, firstName, lastName, gender, gpa, level, address);
            university.addStudent(student);
            XMLUtil.saveStudentsToXML(XML_FILE_PATH, university);
        }

        if (!errorMessages.isEmpty()) {
            request.setAttribute("errorMessages", errorMessages);
            request.getRequestDispatcher("studentForm.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("index.jsp");
    }


    private void searchStudentByStudentID(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String studentID = request.getParameter("studentID");
        List<Student> student = university.findStudentsByStudentID(studentID);
        request.setAttribute("students", student);
        RequestDispatcher dispatcher = request.getRequestDispatcher("studentDetail.jsp");
        dispatcher.forward(request, response);
    }

    private void searchStudentByFirstName(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String firstName = request.getParameter("firstName");
        List<Student> students = university.findStudentsByFirstName(firstName);
        request.setAttribute("students", students);
        RequestDispatcher dispatcher = request.getRequestDispatcher("studentDetail.jsp");
        dispatcher.forward(request, response);
    }

    private void searchStudentByGpa(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        double gpa = Double.parseDouble(request.getParameter("gpa"));
        List<Student> students = university.findStudentsByGpa(gpa);
        request.setAttribute("students", students);
        RequestDispatcher dispatcher = request.getRequestDispatcher("studentDetail.jsp");
        dispatcher.forward(request, response);
    }

    private void searchStudentByLastName(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String lastName = request.getParameter("lastName");
        List<Student> students = university.findStudentsByLastName(lastName);
        request.setAttribute("students", students);
        RequestDispatcher dispatcher = request.getRequestDispatcher("studentDetail.jsp");
        dispatcher.forward(request, response);
    }

    // Search by Gender
    private void searchStudentByGender(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String gender = request.getParameter("gender");
        List<Student> students = university.findStudentsByGender(gender);
        request.setAttribute("students", students);
        RequestDispatcher dispatcher = request.getRequestDispatcher("studentDetail.jsp");
        dispatcher.forward(request, response);
    }

    // Search by Level
    private void searchStudentByLevel(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int level = Integer.parseInt(request.getParameter("level"));
        List<Student> students = university.findStudentsByLevel(level);
        request.setAttribute("students", students);
        RequestDispatcher dispatcher = request.getRequestDispatcher("studentDetail.jsp");
        dispatcher.forward(request, response);
    }

    // Search by Address
    private void searchStudentByAddress(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String address = request.getParameter("address");
        List<Student> students = university.findStudentsByAddress(address);
        request.setAttribute("students", students);
        RequestDispatcher dispatcher = request.getRequestDispatcher("studentDetail.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("studentId");
        if (id == null || id.isEmpty()) {
            response.getWriter().write("Error: Student ID is missing.");
            return;
        }

        boolean removed = university.removeStudent(id);

        if (!removed) {
            response.getWriter().write("Error: Student with ID " + id + " not found.");
            return;
        }

        try {
            XMLUtil.saveStudentsToXML(XML_FILE_PATH, university);
            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            throw new RuntimeException("Error saving students to XML after deletion: " + e.getMessage(), e);
        }
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String studentID = request.getParameter("studentID").trim();
        String firstName = request.getParameter("firstName").trim();
        String lastName = request.getParameter("lastName").trim();
        String gender = request.getParameter("gender").trim();
        String gpaString = request.getParameter("gpa").trim();
        String levelString = request.getParameter("level").trim();
        String address = request.getParameter("address").trim();

        // Validate inputs
        List<String> errorMessages = university.validateStudent(
                studentID, firstName, lastName, gender, gpaString, levelString, address, true
        );

        if (!errorMessages.isEmpty()) {
            // Send back the error messages and user input to the form
            request.setAttribute("errorMessages", errorMessages);
            request.setAttribute("studentID", studentID);
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("gender", gender);
            request.setAttribute("gpa", gpaString);
            request.setAttribute("level", levelString);
            request.setAttribute("address", address);

            request.getRequestDispatcher("updateStudent.jsp").forward(request, response);
            return;
        }

        // Find the student to update
        List<Student> students = university.findStudentsByStudentID(studentID);

        if (!students.isEmpty()) {
            // Update student details
            Student student = students.get(0);
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setGender(gender);
            student.setGpa(Double.parseDouble(gpaString));
            student.setLevel(Integer.parseInt(levelString));
            student.setAddress(address);

            // Save the updated student list to XML
            XMLUtil.saveStudentsToXML(XML_FILE_PATH, university);

            request.setAttribute("students", students);
            request.getRequestDispatcher("studentDetail.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Student with ID " + studentID + " not found.");
            request.getRequestDispatcher("updateStudent.jsp").forward(request, response);
        }
    }

    private void sortByID(List<Student> students, String sortOrder) {
        if ("ascending".equals(sortOrder)) {
            students.sort(Comparator.comparing(Student::getStudentID));
        } else {
            students.sort(Comparator.comparing(Student::getStudentID).reversed());
        }
    }

    // Sorting by First Name
    private void sortByFirstName(List<Student> students, String sortOrder) {
        if ("ascending".equals(sortOrder)) {
            students.sort(Comparator.comparing(Student::getFirstName));
        } else {
            students.sort(Comparator.comparing(Student::getFirstName).reversed());
        }
    }

    // Sorting by GPA
    private void sortByGPA(List<Student> students, String sortOrder) {
        if ("ascending".equals(sortOrder)) {
            students.sort(Comparator.comparingDouble(Student::getGpa));
        } else {
            students.sort(Comparator.comparingDouble(Student::getGpa).reversed());
        }
    }

    // Sorting by Level
    private void sortByLevel(List<Student> students, String sortOrder) {
        if ("ascending".equals(sortOrder)) {
            students.sort(Comparator.comparingInt(Student::getLevel));
        } else {
            students.sort(Comparator.comparingInt(Student::getLevel).reversed());
        }
    }




}
