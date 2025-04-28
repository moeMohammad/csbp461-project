<%-- Document : create_post Created on : 27 Apr 2025, 4:51:00 PM Author : Aziz
--%> <%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="css/index.css" />
    <link rel="stylesheet" href="css/create_post.css" />
    <title>Create New Post</title>
  </head>
  <body>
    <div class="layout_container">
      <%@ include file="navbar.jsp" %>
      <form action="PostCreateServlet" method="post" class="post_form">
        <h2 class="post_header">What's on your mind?</h2>

        <input
          type="text"
          name="title"
          placeholder="Title"
          class="post_form_title"
          required
        /><br /><br />

        <textarea
          name="content"
          rows="10"
          cols="50"
          placeholder="Let's talk about..."
          class="post_form_content"
          required
        ></textarea
        ><br /><br />

        <input type="submit" value="Publish Post" class="post_form_publish" />
      </form>
    </div>
  </body>
</html>
