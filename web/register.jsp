<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Registration</title>
    <link rel="stylesheet" type="text/css" href="css/authentication.css">
</head>
<body>
    <div class="layout-container">
        <div class="form-container">
            <h1 class="form-title">Register New Account</h1>
            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null && !errorMessage.isEmpty()) {
            %>
                <p class="error-message"><%= errorMessage %></p>
            <%
                }
            %>

            <form action="RegisterServlet" method="POST">
                <div class="form-group">
                    <label for="fname" class="form-label">First Name*</label>
                    <input type="text" id="fname" name="fname" class="form-input" required>
                </div>
                <div class="form-group">
                    <label for="lname" class="form-label">Last Name</label>
                    <input type="text" id="lname" name="lname" class="form-input">
                </div>
                <div class="form-group">
                    <label for="email" class="form-label">Email*</label>
                    <input type="email" id="email" name="email" class="form-input" required>
                </div>
                <div class="form-group">
                    <label for="password" class="form-label">Password*</label>
                    <input type="password" id="password" name="password" class="form-input" required>
                </div>
                <div class="form-group">
                    <label for="confirm_password" class="form-label">Confirm Password*</label>
                    <input type="password" id="confirm_password" name="confirm_password" class="form-input" required>
                </div>
                <div class="form-group">
                    <button type="submit" class="button button-primary">Register</button>
                </div>
            </form>
            <p class="form-link">Already have an account? <a href="login.jsp">Login here</a></p>
        </div> 
    </div> 
</body>
</html>