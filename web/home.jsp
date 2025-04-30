<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Post" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Home Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css" />
  </head>
  <body>
    <%@ include file="navbar.jsp" %>

    <div class="page-content">
      <h1>Latest Posts</h1>
      <div class="posts_container">
        <%
            List<Post> posts = (List<Post>) request.getAttribute("posts");
            if (!posts.isEmpty()) {
                for (Post post : posts) {
        %>
        <a href="ViewPost?id=<%= post.getId() %>" class="post" >
            <div class="post_container">
                <div class="post_header">
                    <div class="post_title">
                        <%= post.getTitle() %>
                    </div>
                    <div class="post_profile_card">
                        <div class="profile-card-image">
                            <c:set var="profilePicPath" value="${pageContext.request.contextPath}/uploads/profile_pics/default.png" />
                            <c:if test="${not empty profileUser.profilePictureFilename}">
                                <c:set var="profilePicPath" value="${pageContext.request.contextPath}/uploads/profile_pics/${profileUser.profilePictureFilename}" />
                            </c:if>
                            <img src="${profilePicPath}" alt="${profileUser.fname}'s Profile Picture" class="profile-pic">
                        </div>
                        <%= post.getAuthor() %>
                    </div>
                </div>
                <div class="post_content">
                    <%= post.getContent() %>
                </div>
            </div>
        </a>
        <%
                }
            }
        %>
      </div>
    </div>
  </body>
</html>
