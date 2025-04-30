<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Redirect to login if user is not in session --%>
<c:if test="${empty sessionScope.user}">
    <c:redirect url="/login.jsp"/>
</c:if>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/authentication.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/edit-profile.css">
</head>
<body>
    <%@ include file="navbar.jsp" %>

    <div class="edit-profile-container">
        <div class="form-container"> <%-- Reuse form container style --%>
            <h1 class="form-title">Edit Your Profile</h1>

            <c:if test="${not empty requestScope.errorMessage}">
                <p class="error-message">${requestScope.errorMessage}</p>
            </c:if>
            <c:if test="${not empty requestScope.successMessage}">
                <p class="success-message">${requestScope.successMessage}</p>
            </c:if>

            <div class="current-pic-container">
                <span class="current-pic-label">Current Profile Picture</span>
                <c:set var="profilePicPath" value="${pageContext.request.contextPath}/uploads/profile_pics/default.png" />
                <c:if test="${not empty sessionScope.user.profilePictureFilename}">
                    <c:set var="profilePicPath" value="${pageContext.request.contextPath}/uploads/profile_pics/${sessionScope.user.profilePictureFilename}" />
                </c:if>
                <img src="${profilePicPath}" alt="Current Profile Picture" class="current-pic">
            </div>

            <form action="${pageContext.request.contextPath}/ProfileUpdateServlet" method="POST" enctype="multipart/form-data">

                <div class="form-group">
                    <label for="profile_picture" class="form-label">Upload New Profile Picture (Optional):</label>
                    <input type="file" id="profile_picture" name="profile_picture" class="form-input" accept="image/png, image/jpeg, image/gif">
                </div>

                <div class="form-group">
                    <label for="bio" class="form-label">Bio:</label>
                    <textarea id="bio" name="bio" class="form-input" rows="5" placeholder="Tell us a little about yourself...">${sessionScope.user.bio}</textarea>
                </div>

                <div class="form-group">
                    <button type="submit" class="button button-primary">Save Changes</button>
                </div>
            </form>
             <p class="form-link" style="margin-top: 15px;">
                 <a href="${pageContext.request.contextPath}/UserProfileServlet?userId=${sessionScope.user.id}">Back to Profile</a>
             </p>
        </div>
    </div>
</body>
</html>
