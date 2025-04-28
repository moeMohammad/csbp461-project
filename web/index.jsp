<%@ page import="model.User" %> <%@ page contentType="text/html;charset=UTF-8"
language="java" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Home Page</title>
    <link rel="stylesheet" href="css/index.css" />
  </head>
  <body>
    <%@ include file="navbar.jsp" %>

    <div class="page-content">
      <h1>Page Content</h1>
      <p>${user}</p>
    </div>
  </body>
</html>
