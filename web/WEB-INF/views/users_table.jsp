<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 17.03.2017
  Time: 0:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:forEach items="${requestScope.users}" var="user">
    <tr>
        <td>${user.getId()}</td>
        <td>${user.getName()}</td>
        <td>${user.getPassword()}</td>
        <td>${user.getRole().getName()}</td>
        <td>${user.getEmail()}</td>
        <td>
            <sec:authorize access="hasAuthority('PM_DELETE')">
            <button class="delete" data-value="${user.getId()}">Remove</button>
            </sec:authorize>
        </td>
    </tr>
</c:forEach>
