<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 11.05.2017
  Time: 22:34
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:forEach items="${requestScope.movies}" var="movie">
    <tr>
        <td>${movie.getId()}</td>
        <td>${movie.getName()}</td>
        <td>${movie.getDuration()}</td>
        <td>${movie.getAnnotation()}</td>
        <td>
            <sec:authorize access="hasAuthority('PM_DELETE')">
                <button class="delete" data-value="${movie.getId()}">Remove</button>
            </sec:authorize>
        </td>
    </tr>
</c:forEach>