<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 30.04.2017
  Time: 23:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Seat</title>
    <link rel="stylesheet" href="resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="resources/css/main.css">
    <script src="resources/scripts/jquery/jquery-3.2.1.min.js"></script>
    <script src="resources/scripts/tether/tether.min.js"></script>
    <script src="resources/scripts/bootstrap/bootstrap.min.js"></script>

</head>
<body>
<div id="wrapper">
    <table id="seats" class="table">
        <thead class="thead-inverse">
        <tr>
            <th>ID</th>
            <th>Row #</th>
            <th>Seat #</th>
            <th>Seat status</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td></td>
            <td class="input-group">
                <input id="row" type="text" placeholder="Row #..." class="form-control">
            </td>
            <td>
                <input id="seat" type="text" placeholder="Seat #..." class="form-control">
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
        $("#seats").find("tbody tr").not(":last").remove();
        var tbody = $("tbody");
        loader.show();
        $.ajax({
            url: "seats",
            type: "GET",
            success: function (data) {
                tbody.prepend(data);
            },
            complete: function () {
                loader.hide();
            }
        });
    }

    function deleteSeat() {
        console.log($(this).data("value"));
        $.ajax({
            url: "seats/" + $(this).data("value"),
            type: "DELETE",
            success: loadTable
        });
    }

    function addSeat() {
        var inputRow = $("#row");
        var inputSeat = $("#seat");

        $.ajax({
            url: "seats",
            type: "POST",
            data: {"row_number": inputRow.val(), "seat_number": inputSeat.val()},
            success: loadTable
        });

        inputRow.val("");
        inputSeat.val("");
    }

    $(document).ready(function() {
        loader.hide();
        var table = $("#seats");
        table.on("click", "#add", addSeat);
        table.on("click", "button.delete", deleteSeat);
        loadTable();
    })
</script>
</body>
</html>