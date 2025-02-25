<%@ page import="com.studentApp.Model.Student" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Student Information</title>
    <link rel="stylesheet" type="text/css" href="css/style2.css">
    <script>
        function validateForm(event) {
            // Prevent form submission until validation passes
            event.preventDefault();

            // Retrieve form fields
            const studentID = document.getElementById("studentID").value.trim();
            const firstName = document.getElementById("firstName").value.trim();
            const lastName = document.getElementById("lastName").value.trim();
            const gender = document.getElementById("gender").value.trim();
            const gpa = document.getElementById("gpa").value.trim();
            const level = document.getElementById("level").value.trim();
            const address = document.getElementById("address").value.trim();

            // Error tracking
            let isValid = true;
            let errorMessage = "";

            // Validate student ID (example: must be alphanumeric)
            if (!/^[A-Za-z0-9]+$/.test(studentID)) {
                errorMessage += "Invalid Student ID.\n";
                isValid = false;
            }

            // Validate first name
            if (firstName === "" || !/^[A-Za-z\s]+$/.test(firstName)) {
                errorMessage += "Invalid First Name.\n";
                isValid = false;
            }

            // Validate last name
            if (lastName === "" || !/^[A-Za-z\s]+$/.test(lastName)) {
                errorMessage += "Invalid Last Name.\n";
                isValid = false;
            }

            // Validate gender
            if (gender !== "Male" && gender !== "Female" && gender !== "Other") {
                errorMessage += "Gender must be 'Male', 'Female', or 'Other'.\n";
                isValid = false;
            }

            // Validate GPA (example: between 0.0 and 4.0)
            if (isNaN(gpa) || gpa < 0 || gpa > 4) {
                errorMessage += "GPA must be a number between 0.0 and 4.0.\n";
                isValid = false;
            }

            // Validate level (example: integer between 1 and 8)
            if (isNaN(level) || parseInt(level) < 1 || parseInt(level) > 8) {
                errorMessage += "Level must be an integer between 1 and 4.\n";
                isValid = false;
            }

            // Validate address (example: not empty)
            if (address === "" || !/^[A-Za-z\s]+$/.test(address)) {
                errorMessage += "invalid Address.\n";
                isValid = false;
            }

            // If invalid, alert the user and prevent submission
            if (!isValid) {
                alert(errorMessage);
                return false;
            }

            // If all validations pass, submit the form
            document.getElementById("updateStudentForm").submit();
        }
    </script>
</head>
<body>
<div class="container">
    <h1>Update Student Information</h1>

    <!-- Form for searching student by ID -->
    <form action="StudentServlet" method="post">
        <input type="hidden" name="action" value="update">
        <label for="studentId">Enter Student ID to update:</label>
        <input type="text" id="studentId" name="studentId" required>
        <button type="submit" class="button">Search</button>
    </form>

    <!-- If student data exists, show the update form -->
    <%
        // Get the studentId from the request
        String studentId = request.getParameter("studentId");
        System.out.println("Received student ID from form: " + studentId);

        // Ensure the studentId is not null or empty
        if (studentId != null && !studentId.trim().isEmpty()) {
            java.util.List<com.studentApp.Model.Student> students = (java.util.List<com.studentApp.Model.Student>) request.getAttribute("students");

            // Ensure the students list is not null or empty
            if (students != null && !students.isEmpty()) {
                Student matchedStudent = null;

                // Find the student by ID
                for (Student student : students) {
                    if (student.getStudentID().equals(studentId.trim())) {
                        matchedStudent = student;
                        break;
                    }
                }

                if (matchedStudent != null) {
    %>
    <!-- Update student form -->
    <form id="updateStudentForm" action="StudentServlet" method="post" onsubmit="validateForm(event)">
        <input type="hidden" name="action" value="updateStudent">

        <fieldset>
            <legend>Student Information</legend>
            <label for="studentID">Student ID:</label>
            <input type="text" name="studentID" id="studentID" value="<%= matchedStudent.getStudentID() %>" readonly required>

            <label for="firstName">First Name:</label>
            <input type="text" name="firstName" id="firstName" value="<%= matchedStudent.getFirstName() %>" required>

            <label for="lastName">Last Name:</label>
            <input type="text" name="lastName" id="lastName" value="<%= matchedStudent.getLastName() %>" required>

            <label for="gpa">GPA:</label>
            <input type="number" name="gpa" id="gpa" value="<%= matchedStudent.getGpa() %>" step="0.01" required>

            <label for="level">Level:</label>
            <input type="number" name="level" id="level" value="<%= matchedStudent.getLevel() %>" required>

            <label for="gender">Gender:</label>
            <select name="gender" id="gender" required>
                <option value="Male" <%= matchedStudent.getGender().equalsIgnoreCase("Male") ? "selected" : "" %>>Male</option>
                <option value="Female" <%= matchedStudent.getGender().equalsIgnoreCase("Female") ? "selected" : "" %>>Female</option>
                <option value="Other" <%= matchedStudent.getGender().equalsIgnoreCase("Other") ? "selected" : "" %>>Other</option>
            </select>
            <br>
            <label for="address">Address:</label>
            <input type="text" name="address" id="address" value="<%= matchedStudent.getAddress() %>" required>

            <button type="submit" class="button">Update Student</button>
        </fieldset>
    </form>
    <%
    } else {
    %>
    <p class="error-message">Student not found. Searched for: <%= studentId %></p>
    <%
        }
    } else {
    %>
    <p class="error-message">No student records available to search.</p>
    <%
            }
        }
    %>
</div>
</body>
</html>
