<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 12.05.2017
  Time: 0:21
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Seats</title>
    <c:url value="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.min.css" var="bootstrap_css" />
    <link rel="stylesheet" href="${bootstrap_css}">
    <c:url value="${pageContext.request.contextPath}/resources/css/main.css" var="main_css" />
    <link rel="stylesheet" href="${main_css}">
    <c:url value="${pageContext.request.contextPath}/resources/scripts/jquery/jquery-3.2.1.min.js" var="jquery_js" />
    <script src="${jquery_js}"></script>
    <c:url value="${pageContext.request.contextPath}/resources/scripts/tether/tether.min.js" var="tether_js" />
    <script src="${tether_js}"></script>
    <c:url value="${pageContext.request.contextPath}/resources/scripts/bootstrap/bootstrap.min.js" var="bootstrap_js" />
    <script src="${bootstrap_js}"></script>
    <sec:csrfMetaTags />
</head>
<body>
    <div class="seats">
        <c:forEach items="${requestScope.seats}" var="row">
            <div class="seats-row">
                <c:forEach items="${row}" var="seat">
                    <c:choose>
                        <c:when test="${row != null}">
                            <div class="seats-seat ${seat.getSeatStatus().nameLowerCase()}" data-id="${seat.getId()}">
                                    ${seat.getSeatNumber()}
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="seats-seat empty"></div>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
        </c:forEach>
    </div>

    <button type="button" onclick="buySeat()">Buy</button>
    <button type="button" onclick="reserveSeat()">Reserve</button>
<script>
    var selectedSeats = [];

    function seatClicked() {
        if (selectedSeats.includes(this)) {
            selectedSeats.splice(selectedSeats.indexOf(this), 1);
            $(this).removeClass("selected");

        } else if ($(this).hasClass("available")) {
            selectedSeats.push(this);
            $(this).addClass("selected");
        }
    }

    function buySeat() {
        if (selectedSeats.length > 0) {
            var ids = getIds();

            $.ajax({
                url: "${pageContext.request.contextPath}/seat/buy",
                type: "GET",
                data: {"ids": ids},
                success: function() {
                    location.reload();
                }
            });
        }
    }

    function reserveSeat() {
        if (selectedSeats.length > 0) {
            var ids = getIds();

            $.ajax({
                url: "${pageContext.request.contextPath}/seat/reserve",
                type: "GET",
                data: {"ids": ids},
                success: function() {
                    location.reload();
                }
            });
        }
    }

    function getIds() {
        var ids = [];
        for (var i = 0; i < selectedSeats.length; i++) {
            ids.push($(selectedSeats[i]).data("id"));
        }
        return ids;
    }

    $(document).ready(function() {
        var seats = $(".seats");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");

        $(document).ajaxSend(function(e, xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        });
        seats.on("click", ".seats-seat", seatClicked);
    })
</script>
</body>
</html>
