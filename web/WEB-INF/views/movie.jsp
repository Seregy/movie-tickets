<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 11.05.2017
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movies</title>
    <link rel="stylesheet" href="resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="resources/css/main.css">
    <script src="resources/scripts/jquery/jquery-3.2.1.min.js"></script>
    <script src="resources/scripts/tether/tether.min.js"></script>
    <script src="resources/scripts/bootstrap/bootstrap.min.js"></script>

</head>
<body>
<div id="wrapper">
    <table id="movies" class="table">
        <thead class="thead-inverse">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Duration</th>
            <th>Annotation</th>
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
                <input id="duration" type="text" placeholder="Duration..." class="form-control">
            </td>
            <td>
                <input id="annotation" type="text" placeholder="Annotation..." class="form-control">
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
        $("#movies").find("tbody tr").not(":last").remove();
        var tbody = $("tbody");
        loader.show();
        $.ajax({
            url: "movies",
            type: "GET",
            success: function (data) {
                tbody.prepend(data);
            },
            complete: function () {
                loader.hide();
            }
        });
    }

    function deleteMovie() {
        console.log($(this).data("value"));
        $.ajax({
            url: "movies/" + $(this).data("value"),
            type: "DELETE",
            success: loadTable
        });
    }

    function addMovie() {
        var inputName = $("#name");
        var inputDuration = $("#duration");
        var inputAnnotation = $("#annotation");

        $.ajax({
            url: "movies",
            type: "POST",
            data: {"name": inputName.val(), "duration": inputDuration.val(),
            "annotation": inputAnnotation.val()},
            success: loadTable
        });

        inputName.val("");
        inputDuration.val("");
        inputAnnotation.val("");
    }

    $(document).ready(function() {
        loader.hide();
        var table = $("#movies");
        table.on("click", "#add", addMovie);
        table.on("click", "button.delete", deleteMovie);
        loadTable();
    })
</script>
</body>
</html>