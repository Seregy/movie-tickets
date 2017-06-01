<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width = device-width, initial-scale = 1">
    <title>Profile</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/font-awesome.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/profile.css">
    <link href='https://fonts.googleapis.com/css?family=Cormorant' rel='stylesheet' type='text/css'>
    <script src="${pageContext.request.contextPath}/resources/scripts/jquery/jquery-3.2.1.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/bootstrap/bootstrap.js"></script>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#collapsed-menu" aria-expanded="false">
                    <span class="sr-only"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <div class="collapse navbar-collapse" id="collapsed-menu">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="#">Главная
                            <span class="sr-only">(current)</span>
                        </a>
                    </li>
                    <li><a href="#">Кнопка1</a></li>
                    <li><a href="#">Кнопка2</a></li>
                    <li class="active"><a href="#">Личный кабинет</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <p class="navbar-text"> Имя пользователя <a href="#" class="navbar-link"> [Выход]</a></p>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</div>

<div class="container">
    <ul class="nav nav-tabs ">
        <li class="active"><a data-toggle="tab" href="#tickets">Билеты</a></li>
        <li><a data-toggle="tab" href="#menu1">Персональные данные</a></li>
    </ul>
    <div class="tab-content main-canvas">
        <div id="tickets" class="tab-pane fade in active">
            <ul class="list-inline vertical-align">
                <li class="image-container">
                    <img class="img-responsive"
                         src="${pageContext.request.contextPath}/resources/images/involved-icon-tix.png">
                </li>
                <li><h2>Мои билеты</h2></li>
            </ul>
            <br>
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th class="first-column">№</th>
                        <th>Фильм</th>
                        <th>Кинотеатр</th>
                        <th>Дата</th>
                        <th>Время</th>
                        <th>Цена</th>
                        <th>Ряд</th>
                        <th>Место</th>
                        <th>Статус</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td class="first-column">1</td>
                        <td>Король Артур</td>
                        <td>Баттерфляй Космополит</td>
                        <td>10.11.17</td>
                        <td>10:00</td>
                        <td>120 грн</td>
                        <td>6</td>
                        <td>8</td>
                        <td>Забронировано</td>
                        <td>
                            <button type="button" class="btn btn-info btn-block">Снять бронь</button>
                        </td>
                    </tr>
                    <tr>
                        <td class="first-column">2</td>
                        <td>Король Артур</td>
                        <td>Баттерфляй Космополит</td>
                        <td>10.11.17</td>
                        <td>10:00</td>
                        <td>120 грн</td>
                        <td>6</td>
                        <td>8</td>
                        <td>Забронировано</td>
                        <td>
                            <button type="button" class="btn btn-info btn-block">Снять бронь</button>
                        </td>
                    </tr>
                    <tr>
                        <td class="first-column">3</td>
                        <td>Король Артур</td>
                        <td>Баттерфляй Космополит</td>
                        <td>10.11.17</td>
                        <td>10:00</td>
                        <td>120 грн</td>
                        <td>6</td>
                        <td>8</td>
                        <td>Куплено</td>
                        <td>
                            <button type="button" class="btn btn-primary btn-block">Вернуть</button>
                        </td>
                    </tr>
                    <tr>
                        <td class="first-column">4</td>
                        <td>Король Артур</td>
                        <td>Баттерфляй Космополит</td>
                        <td>10.11.17</td>
                        <td>10:00</td>
                        <td>120 грн</td>
                        <td>6</td>
                        <td>8</td>
                        <td>Куплено</td>
                        <td>
                            <button type="button" class="btn btn-primary btn-block">Вернуть</button>
                        </td>
                    </tr>
                    <tr>
                        <td class="first-column">5</td>
                        <td>Король Артур</td>
                        <td>Баттерфляй Космополит</td>
                        <td>10.11.17</td>
                        <td>10:00</td>
                        <td>120 грн</td>
                        <td>6</td>
                        <td>8</td>
                        <td>Забронировано</td>
                        <td>
                            <button type="button" class="btn btn-info btn-block">Снять бронь</button>
                        </td>
                    </tr>
                    <tr>
                        <td class="first-column">6</td>
                        <td>Король Артур</td>
                        <td>Баттерфляй Космополит</td>
                        <td>10.11.17</td>
                        <td>10:00</td>
                        <td>120 грн</td>
                        <td>6</td>
                        <td>8</td>
                        <td>Забронировано</td>
                        <td>
                            <button type="button" class="btn btn-info btn-block">Снять бронь</button>
                        </td>
                    </tr>
                    <tr>
                        <td class="first-column">7</td>
                        <td>Король Артур</td>
                        <td>Баттерфляй Космополит</td>
                        <td>10.11.17</td>
                        <td>10:00</td>
                        <td>120 грн</td>
                        <td>6</td>
                        <td>8</td>
                        <td>Куплено</td>
                        <td>
                            <button type="button" class="btn btn-primary btn-block">Вернуть</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div class="container">
                <nav style="text-align:center;">
                    <ul class="pagination">
                        <li>
                            <a href="#" aria-label="Previous pull-center">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <li class="active"><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li>
                            <a href="#" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
        <div id="menu1" class="tab-pane fade">
            <h2>Персональные данные</h2><br>
            <div class="container">
                <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                        <div class="input-group input-group-lg">
                            <span class="input-group-addon">Email</span>
                            <label class="form-control" style="color:lightgrey;"> mail@mail.com</label>
                        </div>
                        <br>
                    </div>
                    <div class="col-lg-1 col-md-1 col-sm-12 col-xs-12">
                        <ul>
                            <li class="change-info-lable"><a data-toggle="collapse" href='#mailMenu'>Изменить...</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="tab-content col-lg-6">
                    <div id="mailMenu" class=" collapse">
                        <div class=" menu">
                            <input type="text" class="form-control" placeholder="Введите e-mail">
                            <br>
                            <button class="btn btn-primary" type="button">Изменить</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                        <div class="input-group input-group-lg">
                            <span class="input-group-addon">Пароль</span>
                            <label class="form-control" style="color:lightgrey;"> *********</label>
                        </div>
                        <br>
                    </div>
                    <div class="col-lg-1 col-md-1 col-sm-12 col-xs-12">
                        <ul>
                            <li class="change-info-lable"><a data-toggle="collapse" href='#passMenu'>Изменить...</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="tab-content col-lg-6">
                    <div id="passMenu" class="collapse">
                        <div class="menu">
                            <input type="text" class="form-control" placeholder="Введите пароль"><br>
                            <input type="text" class="form-control" placeholder="Введите пароль повторно"><br>
                            <button class="btn btn-primary" type="button">Изменить</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="footer">
    <div class="row">
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 col-lg-offset-4 col-md-offset-4 col-sm-offset-4 text-center">
            <ul class="list-inline">
                <li><a href="#">Главная</a></li>
                <li><a href="#">|</a></li>
                <li>Перечень:</li>
                <li><a href="">Кнопка</a></li>
                <li><a href="">Кнопка</a></li>
                <li><a href="">Кнопка</a></li>
                <li><a href="">|</a></li>
                <li><a href="">Контакты</a></li>
            </ul>
        </div>
        <div class="col-lg-2 col-md-4 col-sm-3 col-xs-9 col-lg-offset-2  col-sm-offset-0 col-xs-offset-3 pull-right">
            <span>
                <i class="fa fa-envelope-open" aria-hidden="true" style="line-height:6%;"></i>
                e-mail: mail@mail.com
            </span>
            <p>
                <i class="fa fa-address-book" aria-hidden="true" style="line-height:6%;"></i>
                тел. +380000000000
            </p>
        </div>
    </div>
</div>

<script>

    $("#button").click(function () {
        $("p").toggle();
    });

</script>
</body>
</html>