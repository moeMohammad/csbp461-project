<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ page import="model.Post" %>
<%@ page import="model.Comment" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/post.css" />
        <title>Blogger - ${post.getTitle()}</title>
    </head>
    <body>
        <%@ include file="navbar.jsp" %>
        <div class="post">
            ${post.getTitle()}
            ${post.getAuthor()}
            ${post.getContent()}
            ${post.getPfp()}
            ${post.getCreatedAt()}
            ${post.getUpdatedAt()}
        </div>
        <div class="comments">
            <form action="CommentServlet" method="POST">
                <input id="comment" name="Comment" class="form-input" required>
                <button type="submit" class="button button-primary">Comment</button>
            </form>
            <%
                List<Comment> comments = (List<Comment>) request.getAttribute("comments");
                if (!comments.isEmpty()) {
                    for (Comment comment : comments) {
            %>
            <div class="comment_container">
                ${comment.getContent()}
                ${comment.getAuthor()}
                ${comment.getPfp()}
                ${comment.getCreatedAt()}
            </div>
            <%
                    }
                }
            %>
        </div>
    </body>
</html>
