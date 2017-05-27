<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 11.05.2017
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:forEach items="${requestScope.halls}" var="hall">
    <tr>
        <td>${hall.getId()}</td>
        <td>${hall.getName()}</td>
        <td>${hall.getCinema().getId()}</td>
        <td>${hall.getLayout().getRowsAmount()}</td>
        <td>${hall.getLayout().getSeatsAmount()}</td>
        <td>
            <sec:authorize access="hasAuthority('PM_DELETE')">
                <button class="delete" data-value="${hall.getId()}">Remove</button>
            </sec:authorize>
        </td>
    </tr>
</c:forEach>