<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Home Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css" />
  </head>
  <body>
      <form action="PostListServlet"  id="home" method="POST" />
          
      </form>
      <script>
          document.getElementById("home").submit();
      </script>
  </body>
</html>
