<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width = device-width, initial-scale = 1">
    <title>Film</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap-datepicker.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/font-awesome.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/film.css">
    <link href='https://fonts.googleapis.com/css?family=Cormorant' rel='stylesheet' type='text/css'>
    <script src="${pageContext.request.contextPath}/resources/scripts/jquery/jquery-3.2.1.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/bootstrap/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/bootstrap/bootstrap-datepicker.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/bootstrap/bootstrap-datepicker.ru.min.js"></script>
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
                    <li><a href="${pageContext.request.contextPath}/index">Главная</a></li>
                    <li><a href="#">Кнопка1</a></li>
                    <li><a href="#">Кнопка2</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false">
                            <i class="fa fa-map-marker" aria-hidden="true"></i>
                            Киев
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <form class="navbar-form" role="search">
                                    <div class="input-group input-group-sm">
                                        <input type="text" class="form-control" placeholder="Город" maxlength="20">
                                        <span class="input-group-btn">
                                            <button class="btn btn-default btn-xs" type="button">
                                                <span class="glyphicon glyphicon-search"></span>
                                            </button>
                                        </span>
                                    </div>
                                </form>
                            </li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">Львов</a></li>
                            <li><a href="#">Харьков</a></li>
                            <li><a href="#">Одесса</a></li>
                        </ul>
                    </li>
                    <li><a href="#">Вход</a></li>
                    <li><a href="#">Регистрация</a></li>
                </ul>
            </div>
        </div>
    </nav>
</div>

<div class="container">
    <div class="row">
        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
            <div class="thumbnail">
                <img src="resources/images/film5.jpg" alt="...">
                <div class="caption">
                    <p class='infoHeader'>Год</p>
                    <p class='filmInfo'>2017</p>
                    <p class='infoHeader'>Страна</p>
                    <p class='filmInfo'>США</p>
                    <p class='infoHeader'>Жанр</p>
                    <p class='filmInfo'>боевик, драма, приключения, фэнтези</p>
                    <p class='infoHeader'>В главных ролях</p>
                    <p class='filmInfo'>Чарли Ханнэм, Джуд Лоу, Эрик Бана, Аннабелль Уоллис, Эйден Гиллен, Джимон Хунсу,
                        Микаэл Персбрандт, Фредди Фокс, Нил Мэскелл, Гермиона Корфилд</p>
                    <p class='infoHeader'>Режиссер</p>
                    <p class='filmInfo'>Гай Ритчи</p>
                    <p class='infoHeader'>Прокат</p>
                    <p class='filmInfo'>11.05.2017</p>
                    <p class='infoHeader'>Премьерный период ориентировочно до</p>
                    <p class='filmInfo'>24.05.2017</p>
                    <p class='infoHeader'>Продолжительность</p>
                    <p class='filmInfo'>126 мин.</p>
                    <p class='infoHeader'>Возрастное ограничение</p>
                    <p class='filmInfo'>12+</p>
                </div>
            </div>
        </div>
        <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
            <div class="thumbnail" style="padding:15pt;">
                <h3>КОРОЛЬ АРТУР: ЛЕГЕНДА МЕЧА</h3>
                <p class='filmInfo'>Молодой Артур околачивается на окраинах Лондиниума со своей бандой. Своим
                    происхождением он совершенно не дорожит и не интересуется. Так он живёт до момента, когда судьба
                    сводит его с волшебным мечом Экскалибуром. После этого Артур кардинально меняется — влюбляется,
                    присоединяется к движению сопротивления и объединяет вокруг себя людей, чтобы свергнуть тирана
                    Вортигерна, убившего родителей Артура и захватившего трон.</p>
                <div class="input-group">
                    <span class="input-group-addon">Форматы:</span>
                    <div class="btn-group" data-toggle="buttons">
                        <label class="btn btn-primary active">
                            <input type="radio" name="options" id="option1" autocomplete="off" checked> Все
                        </label>
                        <label class="btn btn-primary">
                            <input type="radio" name="options" id="option2" autocomplete="off"> 2D
                        </label>
                        <label class="btn btn-primary">
                            <input type="radio" name="options" id="option3" autocomplete="off"> 3D
                        </label>
                    </div>
                    <div class="input-group pull-right ">
                        <label>
                            <input class="filmDatepicker form-control" type="text">
                        </label>
                        <div class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </div>
                    </div>
                </div>

                <hr/>
                <ul class="list-inline">
                    <li class="cinemaName">Планета Кино</li>
                    <li><a href="#">10:30</a></li>
                    <li><img src="resources/images/3d.png" class="img-responsive center-block"><a href="#">11:30</a>
                    </li>
                    <li><a href="#">14:30</a></li>
                    <li><a href="#">14:30</a></li>
                    <li><a href="#">14:30</a></li>
                    <li><img src="resources/images/3d.png" class="img-responsive center-block"><a href="#">14:30</a>
                    </li>
                    <li><a href="#">14:30</a></li>
                    <li><a href="#">14:30</a></li>
                </ul>
                <hr/>
                <ul class="list-inline">
                    <li class="cinemaName">Планета Кино</li>
                    <li><a href="#">10:30</a></li>
                    <li><a href="#">11:30</a></li>
                    <li><a href="#">14:30</a></li>
                    <li><a href="#">14:30</a></li>
                    <li><a href="#">14:30</a></li>
                    <li><img src="resources/images/3d.png" class="img-responsive center-block"><a href="#">14:30</a>
                    </li>
                    <li><img src="resources/images/3d.png" class="img-responsive center-block"><a href="#">14:30</a>
                    </li>
                    <li><a href="#">14:30</a></li>
                </ul>
                <hr/>
                <ul class="list-inline">
                    <li class="cinemaName">Планета Кино</li>
                    <li><a href="#">10:30</a></li>
                    <li><a href="#">11:30</a></li>
                    <li><a href="#">14:30</a></li>
                    <li><a href="#">14:30</a></li>
                    <li><a href="#">14:30</a></li>
                    <li><a href="#">14:30</a></li>
                    <li><a href="#">14:30</a></li>
                    <li><a href="#">14:30</a></li>
                </ul>
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
    $(document).ready(function () {
        $('.filmDatepicker').datepicker({
            format: "dd-mm-yyyy",
            autoclose: true,
            todayHighlight: true,
            language: "ru"
        });
        var date = new Date();
        var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
        $('.filmDatepicker').datepicker('setDate', today);
    });
</script>
</body>
</html>