<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 11.05.2017
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Roles</title>
    <link rel="stylesheet" href="resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="resources/css/main.css">
    <script src="resources/scripts/jquery/jquery-3.2.1.min.js"></script>
    <script src="resources/scripts/tether/tether.min.js"></script>
    <script src="resources/scripts/bootstrap/bootstrap.min.js"></script>
    <sec:csrfMetaTags />
</head>
<body>
<div id="wrapper">
    <table id="roles" class="table">
        <thead class="thead-inverse">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Permissions</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td></td>
            <td class="input-group">
                <input id="name" type="text" placeholder="Name..." class="form-control">
            </td>
            <td class="input-group">
                <input id="permissions" type="text" placeholder="Permissions..." class="form-control">
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
    var loader;

    function loadTable() {
        $("#roles").find("tbody tr").not(":last").remove();
        var tbody = $("tbody");
        loader.show();
        $.ajax({
            url: "roles",
            type: "GET",
            success: function (data) {
                tbody.prepend(data);
            },
            complete: function () {
                loader.hide();
            }
        });
    }

    function deleteRole() {
        $.ajax({
            url: "roles/" + $(this).data("value"),
            type: "DELETE",
            success: loadTable
        });
    }

    function addRole() {
        var inputName = $("#name");
        var inputPermissions = $("#permissions");

        var permissions = inputPermissions.val().split(",");

        $.ajax({
            url: "roles",
            type: "POST",
            data: {"name": inputName.val(), "permissions_ids": permissions},
            success: loadTable
        });

        inputName.val("");
        inputPermissions.val("");
    }

    $(document).ready(function() {
        loader = $("#loader");
        loader.hide();

        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        $(document).ajaxSend(function(e, xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        });

        var table = $("#roles");
        table.on("click", "#add", addRole);
        table.on("click", "button.delete", deleteRole);
        loadTable();
    })
</script>
</body>
</html>