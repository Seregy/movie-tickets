<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 11.05.2017
  Time: 22:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:forEach items="${requestScope.sessions}" var="session">
    <tr>
        <td><a href="${pageContext.request.contextPath}/session/${session.getId()}">${session.getId()}</a></td>
        <td>${session.getSessionStart()}</td>
        <td>${session.getMovie().getId()}</td>
        <td>${session.getHall().getId()}</td>
        <td><button class="delete" data-value="${session.getId()}">Remove</button></td>
    </tr>
</c:forEach>
