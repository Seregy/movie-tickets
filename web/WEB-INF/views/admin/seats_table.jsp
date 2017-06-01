<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 01.05.2017
  Time: 0:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:forEach items="${requestScope.seats}" var="seat">
    <tr>
        <td>${seat.getId()}</td>
        <td>${seat.getRowNumber()}</td>
        <td>${seat.getSeatNumber()}</td>
        <td>${seat.getSeatStatus()}</td>
        <td><button class="delete" data-value="${seat.getId()}">Remove</button></td>
    </tr>
</c:forEach>
