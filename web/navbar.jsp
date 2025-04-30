<nav class="navbar">
    <div class="navbar-logo">
        <a href="/csbp461-project/">Blogger</a>
    </div>

    <div class="navbar-items">
        <a href="/csbp461-project/create_post.jsp" class="navbar-link">Write</a>
        <%
            if (session.getAttribute("user") == null) {
        %>
        <a  href = "/csbp461-project/login.jsp" class="navbar-link">Sign in</a
        > <a 
            href = "/csbp461-project/register.jsp" class="navbar-button">Get started</a>
        <%
            }
        %>
    </div>
</nav>

