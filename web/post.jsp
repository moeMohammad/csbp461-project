<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ page import="model.Post" %>
<%@ page import="model.Comment" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/post.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css" />
        <title>Blogger - ${post.getTitle()}</title>
    </head>
    <body>
        <%@ include file="navbar.jsp" %>
        <div class="page-content">
            <div class="post_title">
                ${post.title}
            </div>
            <div class="author_info">
                <div class="author_pfp">
                    <c:set var="profilePicPath" value="${pageContext.request.contextPath}/uploads/profile_pics/default.png" />
                    <c:if test="${not empty post.pfp}">
                        <c:set var="profilePicPath" value="${pageContext.request.contextPath}/uploads/profile_pics/${post.pfp}" />
                    </c:if>
                    <img src="${profilePicPath}" alt="${profileUser.fname}'s Profile Picture">
                </div>
                <a href="${pageContext.request.contextPath}/UserProfileServlet?userId=${post.authorId}" class="author_link" style="">
                    <div class="author_name">
                        ${post.author}
                    </div> 
                </a>

                <div class="date">${post.createdAt.toLocalDateTime().toLocalDate()}</div>
            </div>
            <div class="post">
                ${post.content}
            </div>
            <hr class="line_seperator"/>
            <form action="CommentServlet?post_id=${post.getId()}" method="POST" class="comment_form">
                <textarea id="comment-textarea" name="content"></textarea>
                <button type="submit" class="button button-primary">Comment</button>
            </form>
            <div class="comments">

                <%                    List<Comment> comments = (List<Comment>) request.getAttribute("comments");
                    if (!comments.isEmpty()) {
                        for (Comment comment : comments) {
                            String profilePicPath = request.getContextPath() + "/uploads/profile_pics/" + comment.getPfp();
                            System.out.println("pfp from jsp: " + comment.getPfp());
                %>

                <div class="comment_container">
                    <div class="comment_header">
                        <div class="comment_author_pfp">
                            <img src="<%=profilePicPath%>" >
                        </div>
                        <div class="comment_author_date">
                            <div class="comment_author_name">
                                <%= comment.getAuthor()%>
                            </div>
                            <div class="comment_date">
                                <%= comment.getCreatedAt().toLocalDateTime().toLocalDate()%>
                            </div>
                        </div>
                    </div>
                    <div class="comment_body">
                        <%= comment.getContent()%>
                    </div>
                    <hr class="comment_seperator"/>
                </div>
                <%
                        }
                    }
                %>
            </div>
        </div>
    </body>
    <script>
        const textarea = document.getElementById('comment-textarea');

        textarea.addEventListener('input', () => {
            textarea.style.height = 'auto'; // Reset height
            textarea.style.height = textarea.scrollHeight + 'px'; // Set new height
        });
    </script>
</html>
