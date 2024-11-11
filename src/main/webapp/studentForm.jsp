<%--
  Created by IntelliJ IDEA.
  User: E.J.S
  Date: 11/9/2024
  Time: 7:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <title>Enter Student Information</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div class="container">
    <h2>Enter Student Information</h2>
    <%
        int numStudents = Integer.parseInt(request.getParameter("numStudents"));
    %>
    <form action="StudentServlet" method="post">
        <input type="hidden" name="action" value="addStudents">
        <input type="hidden" name="numStudents" value="<%= numStudents %>">

        <%
            for (int i = 1; i <= numStudents; i++) {
        %>
        <fieldset>
            <legend>Student <%= i %></legend>
            <label for="studentID<%= i %>">Student ID:</label>
            <input type="text" name="studentID<%= i %>" required>

            <label for="firstName<%= i %>">First Name:</label>
            <input type="text" name="firstName<%= i %>" required>

            <label for="lastName<%= i %>">Last Name:</label>
            <input type="text" name="lastName<%= i %>" required>

            <label for="gender<%= i %>">Gender:</label>
            <input type="text" name="gender<%= i %>" required>

            <label for="gpa<%= i %>">GPA:</label>
            <input type="text" name="gpa<%= i %>" required>

            <label for="level<%= i %>">Level:</label>
            <input type="number" name="level<%= i %>" required>

            <label for="address<%= i %>">Address:</label>
            <input type="text" name="address<%= i %>" required>
        </fieldset>
        <%
            }
        %>

        <button type="submit" class="button">Submit All Students</button>
    </form>
    <a href="index.jsp" class="button">Back to Menu</a>
</div>
</body>
</html>
