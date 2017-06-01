<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 30.04.2017
  Time: 23:10
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tickets</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
    <script src="${pageContext.request.contextPath}/resources/scripts/jquery/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/tether/tether.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/bootstrap/bootstrap.min.js"></script>
    <sec:csrfMetaTags />
</head>
<body>
<div id="wrapper">
    <table id="tickets" class="table">
        <thead class="thead-inverse">
        <tr>
            <th>ID</th>
            <th>Seat ID</th>
            <th>User ID</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td></td>
            <td class="input-group">
                <input id="seat" type="text" placeholder="Seat ID..." class="form-control">
            </td>
            <td>
                <input id="user" type="text" placeholder="User ID..." class="form-control">
            </td>
            <td>
                <span class="input-group-btn">
                    <button id="add" class="btn btn-default" type="button">
                        Add
                    </button>
                </span>
            </td>
        </tr>
        </tbody>
    </table>
    <img id="loader" src="${pageContext.request.contextPath}/resources/images/loader.svg">
</div>

<script>
    var loader = $("#loader");

    function loadTable() {
        $("#tickets").find("tbody tr").not(":last").remove();
        var tbody = $("tbody");
        loader.show();
        $.ajax({
            url: "tickets",
            type: "GET",
            success: function (data) {
                tbody.prepend(data);
            },
            complete: function () {
                loader.hide();
            }
        });
    }

    function deleteTicket() {
        $.ajax({
            url: "tickets/" + $(this).data("value"),
            type: "DELETE",
            success: loadTable
        });
    }

    function addTicket() {
        var inputSeat = $("#seat");
        var inputUser = $("#user");

        $.ajax({
            url: "tickets",
            type: "POST",
            data: {"seat_id": inputSeat.val(), "user_id": inputUser.val()},
            success: loadTable
        });

        inputSeat.val("");
        inputUser.val("");
    }

    $(document).ready(function() {
        loader = $("#loader");
        loader.hide();

        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        $(document).ajaxSend(function(e, xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        });

        var table = $("#tickets");
        table.on("click", "#add", addTicket);
        table.on("click", "button.delete", deleteTicket);
        loadTable();
    })
</script>
</body>
</html>
