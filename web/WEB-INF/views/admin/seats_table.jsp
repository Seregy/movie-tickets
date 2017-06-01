<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 01.05.2017
  Time: 0:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:forEach items="${requestScope.seats}" var="seat">
    <tr>
        <td>${seat.getId()}</td>
        <td>${seat.getRowNumber()}</td>
        <td>${seat.getSeatNumber()}</td>
        <td>${seat.getSeatStatus()}</td>
        <td>
            <sec:authorize access="hasAuthority('PM_DELETE')">
                <button class="delete" data-value="${seat.getId()}">Remove</button>
            </sec:authorize>
        </td>
    </tr>
</c:forEach>
