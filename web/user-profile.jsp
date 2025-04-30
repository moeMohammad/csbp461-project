<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="model.User" %>
<%@ page import="model.Post" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <c:set var="pageTitle" value="User Profile" />
    <c:if test="${not empty profileUser}">
        <c:set var="pageTitle" value="${profileUser.fname} ${profileUser.lname}'s Profile" />
    </c:if>
    <title>${pageTitle}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user-profile.css">
</head>
<body>
    <%@ include file="navbar.jsp" %>

    <div class="profile-page-container">
        <c:if test="${not empty errorMessage}">
            <p class="error-message">${errorMessage}</p>
        </c:if>

        <c:choose>
            <c:when test="${not empty profileUser}">
                <%-- Profile Card Section (No Change) --%>
                <div class="profile-card">
                    <div class="profile-card-image">
                        <c:set var="profilePicPath" value="${pageContext.request.contextPath}/uploads/profile_pics/default.png" />
                        <c:if test="${not empty profileUser.profilePictureFilename}">
                            <c:set var="profilePicPath" value="${pageContext.request.contextPath}/uploads/profile_pics/${profileUser.profilePictureFilename}" />
                        </c:if>
                        <img src="${profilePicPath}" alt="${profileUser.fname}'s Profile Picture" class="profile-pic">
                    </div>
                    <div class="profile-card-info">
                        <h1 class="profile-name">${profileUser.fname} ${profileUser.lname}</h1>
                        <c:if test="${not empty profileUser.bio}">
                            <p class="profile-bio">${profileUser.bio}</p>
                        </c:if>
                        <p class="profile-joined">
                            Joined:
                            <c:if test="${not empty profileUser.createdAt}">
                                <fmt:formatDate value="${profileUser.createdAt}" type="DATE" dateStyle="medium" />
                            </c:if>
                            <c:if test="${empty profileUser.createdAt}">
                                Date not available
                            </c:if>
                        </p>
                        <c:if test="${not empty sessionScope.user && sessionScope.user.id == profileUser.id}">
                             <div class="profile-actions">
                                <a href="${pageContext.request.contextPath}/edit-profile.jsp" class="edit-profile-button">Edit Profile</a>
                             </div>
                        </c:if>
                    </div>
                </div>
                <%-- End Profile Card Section --%>


                <%-- MODIFIED Posts Section --%>
                <section class="profile-posts-list">
                    <h2>Posts</h2>
                    <c:choose>
                        <c:when test="${not empty userPosts}">
                            <div class="post-feed">
                                <c:forEach var="post" items="${userPosts}">
                                    <%-- Updated structure for each post item --%>
                                    <div class="feed-post-item">
                                        <%-- Author Picture --%>
                                        <div class="feed-post-author-pic-container"> <%-- Optional container --%>
                                            <c:set var="authorPicPath" value="${pageContext.request.contextPath}/uploads/profile_pics/default.png" />
                                            <c:if test="${not empty profileUser.profilePictureFilename}">
                                                <c:set var="authorPicPath" value="${pageContext.request.contextPath}/uploads/profile_pics/${profileUser.profilePictureFilename}" />
                                            </c:if>
                                            <img src="${authorPicPath}" alt="${profileUser.fname}'s Picture" class="feed-post-author-pic">
                                        </div>

                                        <%-- Container for Name and Title (stacked) --%>
                                        <div class="feed-post-details">
                                            <div class="feed-post-author-name">
                                                ${profileUser.fname} ${profileUser.lname}
                                            </div>
                                            <div class="feed-post-title">
                                                <a href="${pageContext.request.contextPath}/ViewPost?id=${post.id}">
                                                    ${post.title}
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                    <%-- End updated structure --%>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <p>This user hasn't published any posts yet.</p>
                        </c:otherwise>
                    </c:choose>
                </section>
                <%-- End MODIFIED Posts Section --%>

            </c:when>
            <c:otherwise>
                <c:if test="${empty errorMessage}">
                    <p>Could not load user profile.</p>
                </c:if>
            </c:otherwise>
        </c:choose>
    </div> <%-- End profile-page-container --%>

</body>
</html>
