<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 17.03.2017
  Time: 0:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:forEach items="${requestScope.tickets}" var="ticket">
    <tr>
        <td>${ticket.getId()}</td>
        <td>${ticket.getUserId()}</td>
        <td>${ticket.getRow()}</td>
        <td>${ticket.getSeat()}</td>
        <td><button class="delete" data-value="${ticket.getId()}">Remove</button></td>
    </tr>
</c:forEach>
