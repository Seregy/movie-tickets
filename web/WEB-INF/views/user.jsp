<%--
  Created by IntelliJ IDEA.
  User: Seregy
  Date: 30.04.2017
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
    <link rel="stylesheet" href="resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="resources/css/main.css">
    <script src="resources/scripts/jquery/jquery-3.2.1.min.js"></script>
    <script src="resources/scripts/tether/tether.min.js"></script>
    <script src="resources/scripts/bootstrap/bootstrap.min.js"></script>

</head>
<body>
<div id="wrapper">
    <table id="users" class="table">
        <thead class="thead-inverse">
        <tr>
            <th>ID</th>
            <th>Full name</th>
            <th>User name</th>
            <th>Password</th>
            <th>Password salt</th>
            <th>Email</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td></td>
            <td class="input-group">
                <input id="full_name" type="text" placeholder="Full name..." class="form-control">
            </td>
            <td class="input-group">
                <input id="user_name" type="text" placeholder="User name..." class="form-control">
            </td>
            <td class="input-group">
                <input id="password" type="text" placeholder="Password..." class="form-control">
            </td>
            <td class="input-group">
                <input id="password_salt" type="text" placeholder="Password salt..." class="form-control">
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
    <img id="loader" src="resources/images/loader.svg">
</div>

<script>
    var loader = $("#loader");

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
        console.log($(this).data("value"));
        $.ajax({
            url: "users/" + $(this).data("value"),
            type: "DELETE",
            success: loadTable
        });
    }

    function addUser() {
        var inputFullName = $("#full_name");
        var inputUserName = $("#user_name");
        var inputPassword = $("#password");
        var inputPasswordSalt = $("#password_salt");
        var inputEmail = $("#email");

        $.ajax({
            url: "users",
            type: "POST",
            data: {"full_name": inputFullName.val(), "user_name": inputUserName.val(),
                "password": inputPassword.val(), "password_salt": inputPasswordSalt.val(),
                "email": inputEmail.val()},
            success: loadTable
        });

        inputFullName.val("");
        inputUserName.val("");
        inputPassword.val("");
        inputPasswordSalt.val("");
        inputEmail.val("");
    }

    $(document).ready(function() {
        loader.hide();
        var table = $("#users");
        table.on("click", "#add", addUser);
        table.on("click", "button.delete", deleteUser);
        loadTable();
    })
</script>
</body>
</html>