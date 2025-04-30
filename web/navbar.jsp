<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.User" %>

<nav class="navbar">
    <div class="navbar-logo">
        <a href="${pageContext.request.contextPath}/">Blogger</a>
    </div>

    <div class="navbar-items">
        <a href="${pageContext.request.contextPath}/create_post.jsp" class="navbar-link">Write</a>
        <%
            User user = (User) session.getAttribute("user");
            if (user == null) {
        %>
        <a href="${pageContext.request.contextPath}/login.jsp" class="navbar-link">Login</a>
        <a href="${pageContext.request.contextPath}/register.jsp" class="navbar-button">Get started</a>
        <%
        } else {
        %>
        <a href="${pageContext.request.contextPath}/UserProfileServlet?userId=${user.getId()}" class="navbar-link">Profile</a>
        <a href="${pageContext.request.contextPath}/LogoutServlet" class="navbar-link">Logout</a>
        <%
            }
        %>
    </div>
</nav>
