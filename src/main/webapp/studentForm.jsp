<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <title>Enter Student Information</title>
    <link rel="stylesheet" type="text/css" href="css/style2.css">
    <script type="text/javascript">
        function validateForm() {
            var numStudents = document.getElementsByName('numStudents')[0].value;
            var studentIds = [];

            for (var i = 1; i <= numStudents; i++) {
                var studentID = document.getElementsByName("studentID" + i)[0].value.trim();
                var firstName = document.getElementsByName("firstName" + i)[0].value.trim();
                var lastName = document.getElementsByName("lastName" + i)[0].value.trim();
                var gpa = parseFloat(document.getElementsByName("gpa" + i)[0].value);
                var address = document.getElementsByName("address" + i)[0].value.trim();
                var level = parseInt(document.getElementsByName("level" + i)[0].value);
                var gender = document.getElementsByName("gender" + i)[0].value;

                // Validate required fields
                if (!studentID || !firstName || !lastName || isNaN(gpa) || !address || !gender || isNaN(level)) {
                    alert("All fields must be filled out for student " + i);
                    return false;
                }

                // Validate Student ID (numeric only)
                if (!/^\d+$/.test(studentID)) {
                    alert("Student ID for student " + i + " must be a number.");
                    return false;
                }

                // Validate GPA (0.0 to 4.0)
                if (gpa < 0 || gpa > 4) {
                    alert("GPA for student " + i + " must be between 0 and 4.");
                    return false;
                }

                // Validate Level (1 to 4)
                if (level < 1 || level > 4) {
                    alert("Level for student " + i + " must be between 1 and 4.");
                    return false;
                }

                // Validate Names (letters and spaces only)
                if (!/^[a-zA-Z\s]+$/.test(firstName)) {
                    alert("First name for student " + i + " must contain only letters and spaces.");
                    return false;
                }
                if (!/^[a-zA-Z\s]+$/.test(lastName)) {
                    alert("Last name for student " + i + " must contain only letters and spaces.");
                    return false;
                }

                // Validate Address (alphanumeric with spaces and punctuation)
                if (!/^[a-zA-Z0-9\s,.-]+$/.test(address)) {
                    alert("Address for student " + i + " contains invalid characters.");
                    return false;
                }

                // Check for duplicate Student IDs
                if (studentIds.includes(studentID)) {
                    alert("Student ID " + studentID + " for student " + i + " is already used.");
                    return false;
                }
                studentIds.push(studentID);
            }

            return true; // Form is valid, proceed with submission
        }
    </script>
</head>
<body>
<div class="container">
    <h2>Enter Student Information</h2>
    <%
        int numStudents = Integer.parseInt(request.getParameter("numStudents"));
        List<String> errorMessages = (List<String>) request.getAttribute("errorMessages");
    %>
    <form action="StudentServlet" method="post" onsubmit="return validateForm()">
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
            <select name="gender<%= i %>" required>
                <option value="">Select Gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>
            <br>
            <label for="gpa<%= i %>">GPA:</label>
            <input type="text" name="gpa<%= i %>" required>

            <label for="level<%= i %>">Level:</label>
            <input type="number" name="level<%= i %>" min="1" max="4" required>

            <label for="address<%= i %>">Address:</label>
            <input type="text" name="address<%= i %>" required>
        </fieldset>
        <%
            }
        %>

        <button type="submit" class="button">Submit All Students</button>
    </form>
    <%
        if (errorMessages != null && !errorMessages.isEmpty()) {
    %>
    <div class="error-messages">
        <ul>
            <% for (String errorMessage : errorMessages) { %>
            <li><%= errorMessage %></li>
            <% } %>
        </ul>
    </div>
    <%
        }
    %>
    <a href="index.jsp" class="button">Back to Menu</a>
</div>
</body>
</html>
