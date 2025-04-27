<%-- 
    Document   : create_post
    Created on : 27 Apr 2025, 4:51:00 PM
    Author     : Aziz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Post</title>
    </head>
    <body>
    <h2>Create a New Post</h2>
    <form action="PostCreateServlet" method="post">
        <label>Title:</label><br/>
        <input type="text" name="title" required/><br/><br/>

        <label>Content:</label><br/>
        <textarea name="content" rows="10" cols="50" required></textarea><br/><br/>

        <input type="submit" value="Publish Post"/>
    </form>
    </body>
</html>
