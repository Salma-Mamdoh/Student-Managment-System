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
import java.util.List;

public class StudentServlet extends HttpServlet {
    private static final String XML_FILE_PATH = "C:\\Users\\E.J.S\\IdeaProjects\\SOA-Assignment\\src\\main\\resources\\students.xml";
    private University university;

    // Initialize the servlet and load students from XML on startup
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
            addStudents(request);
            try {
                XMLUtil.saveStudentsToXML(XML_FILE_PATH, university);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            response.sendRedirect("index.jsp");
        } else if ("search".equals(action)) {
            String searchType = request.getParameter("searchType");
            if ("firstName".equals(searchType)) {
                searchStudentByFirstName(request, response);
            } else if ("gpa".equals(searchType)) {
                searchStudentByGpa(request, response);
            }
        } else if ("delete".equals(action)) {
            deleteStudent(request, response);
        }
    }

    private void addStudents(HttpServletRequest request) {
        int numStudents = Integer.parseInt(request.getParameter("numStudents"));

        for (int i = 1; i <= numStudents; i++) {
            String studentID = request.getParameter("studentID" + i);
            String firstName = request.getParameter("firstName" + i);
            String lastName = request.getParameter("lastName" + i);
            String gender = request.getParameter("gender" + i);
            double gpa = Double.parseDouble(request.getParameter("gpa" + i));
            int level = Integer.parseInt(request.getParameter("level" + i));
            String address = request.getParameter("address" + i);

            Student student = new Student(studentID, firstName, lastName, gender, gpa, level, address);
            university.addStudent(student);
        }
    }

    private void searchStudentByFirstName(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String firstName = request.getParameter("firstName");
        List<Student> students = university.findStudentsByFirstName(firstName);


        // Set the list of students as a request attribute
        request.setAttribute("students", students);

        // Forward to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("studentDetail.jsp");
        dispatcher.forward(request, response);
    }

    private void searchStudentByGpa(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        double gpa = Double.parseDouble(request.getParameter("gpa"));
        List<Student> students = university.findStudentsByGpa(gpa);

        // Set the list of students as a request attribute
        request.setAttribute("students", students);

        // Forward to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("studentDetail.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("studentId");

        // Check if the ID is null or empty
        if (id == null || id.isEmpty()) {
            response.getWriter().write("Error: Student ID is missing.");
            return; // Stop execution if ID is not valid
        }

        // Attempt to remove the student
        boolean removed = university.removeStudent(id);

        // Check if the student was removed successfully
        if (!removed) {
            response.getWriter().write("Error: Student with ID " + id + " not found.");
            return; // Stop execution if the student was not found
        }

        // Save the updated university object to XML
        try {
            XMLUtil.saveStudentsToXML(XML_FILE_PATH, university);
            response.sendRedirect("index.jsp"); // Redirect to the main page
        } catch (Exception e) {
            throw new RuntimeException("Error saving students to XML after deletion: " + e.getMessage(), e);
        }
    }
}
