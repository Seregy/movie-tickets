<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 17.03.2017
  Time: 0:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:forEach items="${requestScope.users}" var="user">
    <tr>
        <td>${user.getId()}</td>
        <td>${user.getFullName()}</td>
        <td>${user.getUserName()}</td>
        <td>${user.getPassword()}</td>
        <td>${user.getSalt()}</td>
        <td>${user.getEmail()}</td>
        <td><button class="delete" data-value="${user.getId()}">Remove</button></td>
    </tr>
</c:forEach>
