<%@ page import="com.studentApp.Model.Student" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sorted Student List</title>
    <link rel="stylesheet" type="text/css" href="css/style2.css">
</head>
<body>
<div class="container">
    <h1>Sorted Student List</h1>

    <form action="StudentServlet" method="post">
        <input type="hidden" name="action" value="sort">

        <label for="sortBy">Sort By:</label>
        <select name="sortBy" id="sortBy" required>
            <option value="ID">Student ID</option>
            <option value="FirstName">First Name</option>
            <option value="GPA">GPA</option>
            <option value="Level">Level</option>
        </select>

        <label for="sortOrder">Sort Order:</label>
        <select name="sortOrder" id="sortOrder" required>
            <option value="ascending">Ascending</option>
            <option value="descending">Descending</option>
        </select>

        <button type="submit" class="button">Sort</button>
    </form>

    <!-- If there is no student data, display the error message -->
    <%
        List<Student> students = (List<Student>) request.getAttribute("students");
        String noDataMessage = (String) request.getAttribute("noData");

        if (noDataMessage != null) {
    %>
    <p class="error-message"><%= noDataMessage %></p>
    <% } else if (students != null && !students.isEmpty()) { %>
    <h2>Sorted Student Table</h2>
    <table>
        <thead>
        <tr>
            <th>Student ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Gender</th>
            <th>GPA</th>
            <th>Level</th>
            <th>Address</th>
        </tr>
        </thead>
        <tbody>
        <% for (Student student : students) { %>
        <tr>
            <td><%= student.getStudentID() %></td>
            <td><%= student.getFirstName() %></td>
            <td><%= student.getLastName() %></td>
            <td><%= student.getGender() %></td>
            <td><%= student.getGpa() %></td>
            <td><%= student.getLevel() %></td>
            <td><%= student.getAddress() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else { %>
    <% } %>
</div>
</body>
</html>
