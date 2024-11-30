<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search for Student</title>
    <link rel="stylesheet" type="text/css" href="css/style2.css">
    <script>
        // JavaScript to toggle input fields based on selected search type
        function toggleSearchField() {
            // Hide all fields initially
            document.querySelectorAll('.searchField').forEach(field => field.style.display = 'none');

            // Get the selected search type
            var searchType = document.querySelector('input[name="searchType"]:checked').value;

            // Show the corresponding field
            document.getElementById("searchBy" + searchType.charAt(0).toUpperCase() + searchType.slice(1)).style.display = 'block';
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
            <input type="radio" name="searchType" value="studentID" onclick="toggleSearchField()" checked>
            Search by Student ID
        </label>
        <label>
            <input type="radio" name="searchType" value="firstName" onclick="toggleSearchField()">
            Search by First Name
        </label>
        <label>
            <input type="radio" name="searchType" value="lastName" onclick="toggleSearchField()">
            Search by Last Name
        </label>
        <label>
            <input type="radio" name="searchType" value="gpa" onclick="toggleSearchField()">
            Search by GPA
        </label>
        <label>
            <input type="radio" name="searchType" value="gender" onclick="toggleSearchField()">
            Search by Gender
        </label>
        <label>
            <input type="radio" name="searchType" value="level" onclick="toggleSearchField()">
            Search by Level
        </label>
        <label>
            <input type="radio" name="searchType" value="address" onclick="toggleSearchField()">
            Search by Address
        </label>

        <!-- Input fields for each search type -->
        <div id="searchByStudentID" class="searchField">
            <label for="studentID">Student ID:</label>
            <input type="text" id="studentID" name="studentID">
        </div>

        <div id="searchByFirstName" class="searchField" style="display: none;">
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName">
        </div>

        <div id="searchByLastName" class="searchField" style="display: none;">
            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" name="lastName">
        </div>

        <div id="searchByGpa" class="searchField" style="display: none;">
            <label for="gpa">GPA:</label>
            <input type="text" id="gpa" name="gpa">
        </div>

        <div id="searchByGender" class="searchField" style="display: none;">
            <label for="gender">Gender:</label>
            <select id="gender" name="gender">
                <option value="">Select Gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>
        </div>

        <div id="searchByLevel" class="searchField" style="display: none;">
            <label for="level">Level:</label>
            <input type="text" id="level" name="level">
        </div>

        <div id="searchByAddress" class="searchField" style="display: none;">
            <label for="address">Address:</label>
            <input type="text" id="address" name="address">
        </div>

        <!-- Submit button -->
        <button type="submit" class="button">Search</button>
    </form>
    <a href="index.jsp" class="button">Back to Menu</a>
</div>
</body>
</html>
