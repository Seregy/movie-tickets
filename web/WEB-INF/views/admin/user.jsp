<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 30.04.2017
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
    <script src="${pageContext.request.contextPath}/resources/scripts/jquery/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/tether/tether.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/bootstrap/bootstrap.min.js"></script>
    <sec:csrfMetaTags />
</head>
<body>
<div id="wrapper">
    <table id="users" class="table">
        <thead class="thead-inverse">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Password</th>
            <th>Role</th>
            <th>Email</th>
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
                <input id="password" type="text" placeholder="Password..." class="form-control">
            </td>
            <td class="input-group">
                <input id="role" type="text" placeholder="Role id..." class="form-control">
            </td>
            <td class="input-group">
                <input id="email" type="text" placeholder="Email..." class="form-control">
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
    var loader;

    function loadTable() {
        $("#users").find("tbody tr").not(":last").remove();
        var tbody = $("tbody");
        loader.show();
        $.ajax({
            url: "users",
            type: "GET",
            success: function (data) {
                tbody.prepend(data);
            },
            complete: function () {
                loader.hide();
            }
        });
    }

    function deleteUser() {
        $.ajax({
            url: "users/" + $(this).data("value"),
            type: "DELETE",
            success: loadTable
        });
    }

    function addUser() {
        var inputName = $("#name");
        var inputPassword = $("#password");
        var inputRole = $("#role");
        var inputEmail = $("#email");

        $.ajax({
            url: "users",
            type: "POST",
            data: {"name": inputName.val(), "password": inputPassword.val(),
                "role_id": inputRole.val(),
                "email": inputEmail.val()},
            success: loadTable
        });

        inputName.val("");
        inputPassword.val("");
        inputRole.val("");
        inputEmail.val("");
    }

    $(document).ready(function() {
        loader = $("#loader");
        loader.hide();

        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        $(document).ajaxSend(function(e, xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        });

        var table = $("#users");
        table.on("click", "#add", addUser);
        table.on("click", "button.delete", deleteUser);
        loadTable();
    })
</script>
</body>
</html>