<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 11.05.2017
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sessions</title>
    <link rel="stylesheet" href="resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="resources/css/main.css">
    <script src="resources/scripts/jquery/jquery-3.2.1.min.js"></script>
    <script src="resources/scripts/tether/tether.min.js"></script>
    <script src="resources/scripts/bootstrap/bootstrap.min.js"></script>

</head>
<body>
<div id="wrapper">
    <table id="sessions" class="table">
        <thead class="thead-inverse">
        <tr>
            <th>ID</th>
            <th>Starting date-time</th>
            <th>Movie ID</th>
            <th>Hall ID</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td></td>
            <td>
                <input id="datetime" type="text" placeholder="Date-time..." class="form-control">
            </td>
            <td>
                <input id="movie" type="text" placeholder="Movie ID..." class="form-control">
            </td>
            <td>
                <input id="hall" type="text" placeholder="Hall ID..." class="form-control">
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
    <img id="loader" src="resources/images/loader.svg">
</div>

<script>
    var loader = $("#loader");

    function loadTable() {
        $("#sessions").find("tbody tr").not(":last").remove();
        var tbody = $("tbody");
        loader.show();
        $.ajax({
            url: "sessions",
            type: "GET",
            success: function (data) {
                tbody.prepend(data);
            },
            complete: function () {
                loader.hide();
            }
        });
    }

    function deleteSession() {
        console.log($(this).data("value"));
        $.ajax({
            url: "sessions/" + $(this).data("value"),
            type: "DELETE",
            success: loadTable
        });
    }

    function addSession() {
        var inputDateTime = $("#datetime");
        var inputMovie = $("#movie");
        var inputHall = $("#hall");

        $.ajax({
            url: "sessions",
            type: "POST",
            data: {"date_time": inputDateTime.val(), "movie_id": inputMovie.val(),
                "hall_id": inputHall.val()},
            success: loadTable
        });

        inputDateTime.val("");
        inputMovie.val("");
        inputHall.val("");
    }

    $(document).ready(function() {
        loader.hide();
        var table = $("#sessions");
        table.on("click", "#add", addSession);
        table.on("click", "button.delete", deleteSession);
        loadTable();
    })
</script>
</body>
</html>
