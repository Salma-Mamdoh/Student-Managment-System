<%--
  Created by IntelliJ IDEA.
  User: E.J.S
  Date: 11/9/2024
  Time: 8:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Student</title>
    <link rel="stylesheet" type="text/css" href="css/style2.css">
</head>
<body>
<div class="container">
    <h2>Add Students</h2>
    <form action="studentForm.jsp" method="post">
        <label for="numStudents">Number of Students to Add:</label>
        <input type="number" id="numStudents" name="numStudents" required min="1">

        <button type="submit" class="button">Submit</button>
    </form>
    <a href="index.jsp" class="button">Back to Menu</a>
</div>
</body>
</html>

