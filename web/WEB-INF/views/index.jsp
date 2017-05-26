<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 20.04.2017
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie tickets database</title>
    <link rel="stylesheet" href="resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="resources/css/main.css">
    <script src="resources/scripts/jquery/jquery-3.2.1.min.js"></script>
    <script src="resources/scripts/tether/tether.min.js"></script>
    <script src="resources/scripts/bootstrap/bootstrap.min.js"></script>
</head>
<body>
    <div id="wrapper">
        <sec:authorize access="isAnonymous()">
            <p>
                You're currently not logged in.
            </p>
            <c:url var="loginUrl" value="/login"/>
            <form class="form-inline" action="${loginUrl}" method="post">
                <fieldset>
                    <legend>Please Login</legend>
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username"/>
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password"/>
                    <div class="form-actions">
                        <button type="submit" class="btn">Log in</button>
                    </div>
                </fieldset>
                <sec:csrfInput />
            </form>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <p>
                Hello, <b><c:out value="${pageContext.request.remoteUser}"/></b>
            </p>
            <c:url var="logoutUrl" value="/logout"/>
            <form class="form-inline" action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <sec:csrfInput />
            </form>
        </sec:authorize>

        <ul>
            <li><a href="${pageContext.request.contextPath}/cinema">Cinemas</a></li>
            <li><a href="${pageContext.request.contextPath}/hall">Halls</a></li>
            <li><a href="${pageContext.request.contextPath}/movie">Movies</a></li>
            <li><a href="${pageContext.request.contextPath}/seat">Seats</a></li>
            <li><a href="${pageContext.request.contextPath}/session">Sessions</a></li>
            <li><a href="${pageContext.request.contextPath}/ticket">Tickets</a></li>
            <li><a href="${pageContext.request.contextPath}/user">Users</a></li>
            <li><a href="${pageContext.request.contextPath}/role">Roles</a></li>
        </ul>
    </div>
</body>
</html>

