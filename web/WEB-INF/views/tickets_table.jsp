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
<c:forEach items="${requestScope.tickets}" var="ticket">
    <tr>
        <td>${ticket.getId()}</td>
        <td>${ticket.getSeat().getId()}</td>
        <td>${ticket.getUser().getId()}</td>
        <td>
            <sec:authorize access="hasRole('ADMIN')">
                <button class="delete" data-value="${ticket.getId()}">Remove</button>
            </sec:authorize>
        </td>
    </tr>
</c:forEach>
