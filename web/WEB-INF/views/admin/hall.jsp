<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 11.05.2017
  Time: 22:05
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Halls</title>
    <link rel="stylesheet" href="../../../resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="../../../resources/css/main.css">
    <script src="../../../resources/scripts/jquery/jquery-3.2.1.min.js"></script>
    <script src="../../../resources/scripts/tether/tether.min.js"></script>
    <script src="../../../resources/scripts/bootstrap/bootstrap.min.js"></script>

</head>
<body>
<div id="wrapper">
    <table id="halls" class="table">
        <thead class="thead-inverse">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Cinema ID</th>
            <th>Rows amount</th>
            <th>Seats amount</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td></td>
            <td class="input-group">
                <input id="name" type="text" placeholder="Name..." class="form-control">
            </td>
            <td>
                <input id="cinema" type="text" placeholder="Cinema ID..." class="form-control">
            </td>
            <td>
                <input id="rows" type="text" placeholder="Rows amount..." class="form-control">
            </td>
            <td>
                <input id="seats" type="text" placeholder="Seats amount..." class="form-control">
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
    <img id="loader" src="../../../resources/images/loader.svg">
</div>

<script>
    var loader = $("#loader");

    function loadTable() {
        $("#halls").find("tbody tr").not(":last").remove();
        var tbody = $("tbody");
        loader.show();
        $.ajax({
            url: "halls",
            type: "GET",
            success: function (data) {
                tbody.prepend(data);
            },
            complete: function () {
                loader.hide();
            }
        });
    }

    function deleteHall() {
        console.log($(this).data("value"));
        $.ajax({
            url: "halls/" + $(this).data("value"),
            type: "DELETE",
            success: loadTable
        });
    }

    function addHall() {
        var inputName = $("#name");
        var inputCinema = $("#cinema");
        var inputRows = $("#rows");
        var inputSeats = $("#seats");

        $.ajax({
            url: "halls",
            type: "POST",
            data: {"name": inputName.val(), "cinema_id": inputCinema.val(),
            "rows_amount": inputRows.val(), "seats_amount": inputSeats.val()},
            success: loadTable
        });

        inputName.val("");
        inputCinema.val("");
        inputRows.val("");
        inputSeats.val("");
    }

    $(document).ready(function() {
        loader.hide();
        var table = $("#halls");
        table.on("click", "#add", addHall);
        table.on("click", "button.delete", deleteHall);
        loadTable();
    })
</script>
</body>
</html>