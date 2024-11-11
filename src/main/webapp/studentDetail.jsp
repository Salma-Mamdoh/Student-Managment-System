<%--
  Created by IntelliJ IDEA.
  User: E.J.S
  Date: 11/9/2024
  Time: 7:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>Student Information</title>
<link rel="stylesheet" type="text/css" href="css/style.css">

<html>
<head>
    <title>Student Details</title>
</head>
<body>
<div class="container">
    <h2>Student Details</h2>

    <%
        // Check if the "students" attribute is null or empty
        java.util.List<com.studentApp.Model.Student> students = (java.util.List<com.studentApp.Model.Student>) request.getAttribute("students");

        if (students != null && !students.isEmpty()) {
    %>
    <div class="student-info">
        <fieldset>
            <legend>Students Found</legend>
            <% for (com.studentApp.Model.Student student : students) { %>
            <p><strong>ID:</strong> <span><%= student.getId() %></span></p>
            <p><strong>First Name:</strong> <span><%= student.getFirstName() %></span></p>
            <p><strong>Last Name:</strong> <span><%= student.getLastName() %></span></p>
            <p><strong>Gender:</strong> <span><%= student.getGender() %></span></p>
            <p><strong>GPA:</strong> <span><%= student.getGpa() %></span></p>
            <p><strong>Level:</strong> <span><%= student.getLevel() %></span></p>
            <p><strong>Address:</strong> <span><%= student.getAddress() %></span></p>
            <hr/> <!-- Separate each student with a line -->
            <% } %>
        </fieldset>
    </div>
    <% } else { %>
    <div class="error-message">
        <p>Student not found.</p>
    </div>
    <% } %>

    <a href="index.jsp" class="button">Return to Main Page</a>
</div>
</body>
</html>
