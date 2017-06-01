<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width = device-width, initial-scale = 1">
    <title>MainPage</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/font-awesome.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/index.css">
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
                    <li class="active"><a href="#">Главная <span class="sr-only">(current)</span></a></li>
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
    <br>
    <p class="blockName">СЕЙЧАС НА ЭКРАНАХ</p>
    <br>
    <div class="row" style="word-wrap:break-word;">
        <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
            <div class="thumbnail">
                <img src="resources/images/film1.jpg" alt="...">
                <div class="caption">
                    <h3>Красавица и чудовище</h3>
                    <p>с 16.03.2017 до 10.05.2017</p>
                </div>
            </div>
        </div>
        <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
            <div class="thumbnail">
                <img src="resources/images/film2.jpg" alt="...">
                <div class="caption">
                    <h3>Стражи галактики. Часть 2</h3>
                    <p>с 04.05.2017 до 31.05.2017</p>
                </div>
            </div>
        </div>
        <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
            <div class="thumbnail">
                <img src="resources/images/film3.jpg" alt="...">
                <div class="caption">
                    <h3>Путешествие во времени</h3>
                    <p>с 13.04.2017 до 10.05.2017</p>
                </div>
            </div>
        </div>
        <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
            <div class="thumbnail">
                <img src="resources/images/film4.jpg" alt="...">
                <div class="caption">
                    <h3>Путешествие во времени</h3>
                    <p>с 13.04.2017 до 10.05.2017</p>
                </div>
            </div>
        </div>
        <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
            <div class="thumbnail">
                <img src="resources/images/film5.jpg" alt="...">
                <div class="caption">
                    <h3>Путешествие во времени</h3>
                    <p>с 13.04.2017 до 10.05.2017</p>
                </div>
            </div>
        </div>
        <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
            <div class="thumbnail">
                <img src="resources/images/film6.jpg" alt="...">
                <div class="caption">
                    <h3>Путешествие во времени</h3>
                    <p>с 13.04.2017 до 10.05.2017</p>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
            <div class="thumbnail">
                <img src="resources/images/film1.jpg" alt="...">
                <div class="caption">
                    <h3>Красавица и чудовище</h3>
                    <p>с 16.03.2017 до 10.05.2017</p>
                </div>
            </div>
        </div>
        <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
            <div class="thumbnail">
                <img src="resources/images/film2.jpg" alt="...">
                <div class="caption">
                    <h3>Стражи галактики. Часть 2</h3>
                    <p>с 04.05.2017 до 31.05.2017</p>
                </div>
            </div>
        </div>
        <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
            <div class="thumbnail">
                <img src="resources/images/film3.jpg" alt="...">
                <div class="caption">
                    <h3>Путешествие во времени</h3>
                    <p>с 13.04.2017 до 10.05.2017</p>
                </div>
            </div>
        </div>
        <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
            <div class="thumbnail">
                <img src="resources/images/film3.jpg" alt="...">
                <div class="caption">
                    <h3>Путешествие во времени</h3>
                    <p>с 13.04.2017 до 10.05.2017</p>
                </div>
            </div>
        </div>
        <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
            <div class="thumbnail">
                <img src="resources/images/film3.jpg" alt="...">
                <div class="caption">
                    <h3>Путешествие во времени</h3>
                    <p>с 13.04.2017 до 10.05.2017</p>
                </div>
            </div>
        </div>
        <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
            <div class="thumbnail">
                <img src="resources/images/film3.jpg" alt="...">
                <div class="caption">
                    <h3>Путешествие во времени</h3>
                    <p>с 13.04.2017 до 10.05.2017</p>
                </div>
            </div>
        </div>
    </div>
</div>

<br>
<div class='comingSoon'>
    <div class="container">
        <br>
        <p class="blockName">СКОРО В ПРОКАТЕ</p>
        <br>
        <div class="row">
            <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
                <div class="thumbnail">
                    <img src="resources/images/film1.jpg" alt="...">
                    <div class="caption">
                        <h3>Красавица и чудовище</h3>
                        <p>с 16.03.2017 до 10.05.2017</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
                <div class="thumbnail">
                    <img src="resources/images/film2.jpg" alt="...">
                    <div class="caption">
                        <h3>Стражи галактики. Часть 2</h3>
                        <p>с 04.05.2017 до 31.05.2017</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
                <div class="thumbnail">
                    <img src="resources/images/film3.jpg" alt="...">
                    <div class="caption">
                        <h3>Путешествие во времени</h3>
                        <p>с 13.04.2017 до 10.05.2017</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
                <div class="thumbnail">
                    <img src="resources/images/film3.jpg" alt="...">
                    <div class="caption">
                        <h3>Путешествие во времени</h3>
                        <p>с 13.04.2017 до 10.05.2017</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
                <div class="thumbnail">
                    <img src="resources/images/film3.jpg" alt="...">
                    <div class="caption">
                        <h3>Путешествие во времени</h3>
                        <p>с 13.04.2017 до 10.05.2017</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-2 col-md-4 col-sm-4 col-xs-12">
                <div class="thumbnail">
                    <img src="resources/images/film3.jpg" alt="...">
                    <div class="caption">
                        <h3>Путешествие во времени</h3>
                        <p>с 13.04.2017 до 10.05.2017</p>
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
</body>
</html>