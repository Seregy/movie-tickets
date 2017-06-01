<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 21.04.2017
  Time: 0:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cinemas</title>
    <link rel="stylesheet" href="../../../resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="../../../resources/css/main.css">
    <script src="../../../resources/scripts/jquery/jquery-3.2.1.min.js"></script>
    <script src="../../../resources/scripts/tether/tether.min.js"></script>
    <script src="../../../resources/scripts/bootstrap/bootstrap.min.js"></script>

</head>
<body>
<div id="wrapper">
    <table id="cinemas" class="table">
        <thead class="thead-inverse">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Location</th>
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
                <input id="location" type="text" placeholder="Location..." class="form-control">
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
        $("#cinemas").find("tbody tr").not(":last").remove();
        var tbody = $("tbody");
        loader.show();
        $.ajax({
            url: "cinemas",
            type: "GET",
            success: function (data) {
                tbody.prepend(data);
            },
            complete: function () {
                loader.hide();
            }
        });
    }

    function deleteCinema() {
        console.log($(this).data("value"));
        $.ajax({
            url: "cinemas/" + $(this).data("value"),
            type: "DELETE",
            success: loadTable
        });
    }

    function addCinema() {
        var inputName = $("#name");
        var inputLocation = $("#location");

        $.ajax({
            url: "cinemas",
            type: "POST",
            data: {"name": inputName.val(), "location": inputLocation.val()},
            success: loadTable
        });

        inputName.val("");
        inputLocation.val("");
    }

    $(document).ready(function() {
        loader.hide();
        var table = $("#cinemas");
        table.on("click", "#add", addCinema);
        table.on("click", "button.delete", deleteCinema);
        loadTable();
    })
</script>
</body>
</html>