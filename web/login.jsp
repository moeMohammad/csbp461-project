<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Login</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/authentication.css">
</head>
<body>
    <div class="layout-container">
        <div class="form-container">
            <h1 class="form-title">Login</h1>
            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null && !errorMessage.isEmpty()) {
            %>
                <p class="error-message"><%= errorMessage %></p>
            <%
                }
            %>

            <form action="LoginServlet" method="POST">
                <div class="form-group">
                    <label for="email" class="form-label">Email*</label>
                    <input type="email" id="email" name="email" class="form-input" required>
                </div>
                <div class="form-group">
                    <label for="password" class="form-label">Password*</label>
                    <input type="password" id="password" name="password" class="form-input" required>
                </div>
                <div class="form-group">
                    <button type="submit" class="button button-primary">Login</button>
                </div>
            </form>
            <p class="form-link">Don't an account? <a href="register.jsp">Register here</a></p>
        </div> 
    </div> 
</body>
</html>