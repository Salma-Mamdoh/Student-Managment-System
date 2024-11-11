<%--
  Created by IntelliJ IDEA.
  User: E.J.S
  Date: 11/9/2024
  Time: 9:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search for Student</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script>
        // JavaScript to toggle input fields based on selected search type
        function toggleSearchField() {
            var searchType = document.querySelector('input[name="searchType"]:checked').value;
            document.getElementById("searchByFirstName").style.display = searchType === "firstName" ? "block" : "none";
            document.getElementById("searchByGpa").style.display = searchType === "gpa" ? "block" : "none";
        }
    </script>
</head>
<body>
<div class="container">
    <h2>Search for Student</h2>
    <form action="StudentServlet" method="post">
        <input type="hidden" name="action" value="search">

        <!-- Radio buttons to select search type -->
        <label>
            <input type="radio" name="searchType" value="firstName" onclick="toggleSearchField()" checked>
            Search by First Name
        </label>
        <label>
            <input type="radio" name="searchType" value="gpa" onclick="toggleSearchField()">
            Search by GPA
        </label>

        <!-- Input for searching by First Name -->
        <div id="searchByFirstName">
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName">
        </div>

        <!-- Input for searching by GPA -->
        <div id="searchByGpa" style="display: none;">
            <label for="gpa">GPA:</label>
            <input type="text" id="gpa" name="gpa">
        </div>

        <button type="submit" class="button">Search</button>
    </form>
    <a href="index.jsp" class="button">Back to Menu</a>
</div>
</body>
</html>
