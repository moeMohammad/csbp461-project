<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Post" %>

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
      <h1>Latest Posts</h1>
      <div class="posts_container">
        <%
            List<Post> posts = (List<Post>) request.getAttribute("posts");
            if (posts != null) {
                for (Post post : posts) {
        %>
        <div class="post">
            <div class="post_title">
                ${post.getTitle()}
            </div>
            <div class="post_content">
                ${post.getContent()}
            </div>
        </div>
        <%
                }
            }
        %>
      </div>
      <p>${user}</p>
    </div>
  </body>
</html>
