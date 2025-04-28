<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home Page</title>
    <link rel="stylesheet" href="css/index.css">
</head>
<body>

    <nav class="navbar">
        <div class="navbar-logo">
            <a href="#">Blogger</a>
        </div>

        <div class="navbar-items">
            <a href="create_post.jsp" class="navbar-link">Write</a>
            <a href="login.jsp" class="navbar-link">Sign in</a>
            <a href="register.jsp" class="navbar-button">Get started</a>
        </div>
    </nav>

    <div class="page-content">
        <h1>Page Content</h1>
        <p>${user}</p>
    </div>

</body>
</html>
