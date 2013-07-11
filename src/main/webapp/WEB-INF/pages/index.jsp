<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Cheat food</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap -->
    <link href="<s:url value="/resources" />/css/bootstrap.min.css" rel="stylesheet" media="screen" type="text/css"/>
    <link href="<s:url value="/resources" />/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>
    <link href="<s:url value="/resources" />/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="<s:url value="/resources" />/css/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" type="text/css"/>


    <script src="<s:url value="/resources"/>/js/jquery-1.10.1.min.js"></script>
    <script src="<s:url value="/resources"/>/js/bootstrap.min.js"></script>

    <script src="<s:url value="/resources"/>/js/jquery.validate.min.js"></script>
    <script src="<s:url value="/resources"/>/js/validation.messages_ru.js"></script>

    <script src="<s:url value="/resources"/>/js/jquery-ui-1.10.3.custom.min.js"></script>
    <script src="<s:url value="/resources"/>/js/jquery-ui.datepicker.ru.js"></script>

    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&key=AIzaSyBwBoJd0Pv2XqjkfAp25UJaehgWOzmajBc&libraries=places&language=RU"></script>
    <!--script src="https://www.google.com/jsapi?sensor=true&key=AIzaSyBwBoJd0Pv2XqjkfAp25UJaehgWOzmajBc&libraries=places&language=RU"></script-->

    <script type="text/javascript" src="<s:url value="/resources"/>/js/markerclusterer.min.js"></script>
    <script type="text/javascript" src="<s:url value="/resources"/>/js/infobox.min.js"></script>

    <script src="<s:url value="/resources"/>/js/gmaps.js"></script>
    <script src="<s:url value="/resources"/>/js/cheatfood.js"></script>
</head>

<body>

<div class="navbar navbar-inverse">
    <div class="navbar-inner">
        <div class="container">

            <!-- .btn-navbar is used as the toggle for collapsed navbar content -->
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>

            <!-- Be sure to leave the brand out there if you want it shown -->
            <a class="brand" href="<c:url value="/"/>">cheat food</a>

            <ul class="nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        Редактировать<b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                                <a id="addMarkerMenu">
                                    <img src="resources/img/pin.png" width="20"/>
                                    <span class="spacer3">Добавить точку</span>
                                </a>
                        </li>
                        <li>
                            <a id="editMarkerMenu">
                                <i class="icon-edit"></i>
                                <span class="spacer3">Редактировать точку</span>
                            </a>
                        </li>
                        <li>
                            <a id="deleteMarkerMenu">
                                <i class="icon-remove"></i>
                                <span class="spacer3">Удалить точку</span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        Категории<b class="caret"></b>
                    </a>
                    <ul id="categoryMenu" class="dropdown-menu">
                    </ul>
                </li>
                <li><a href="#">Вход</a></li>
            </ul>

            <form class="navbar-search pull-left">
                <input type="text" class="search-query input-block-level span5 searchInput" placeholder="Поиск">
            </form>


            <!-- Everything you want hidden at 940px or less, place within here -->
            <div class="nav-collapse collapse">
                <!-- .nav, .navbar-search, .navbar-form, etc -->
                <ul class="nav pull-right">
                    <li><a href="#">Манифест</a></li>
                    <li><a href="#">Пользователям</a></li>
                </ul>
            </div>

        </div>
    </div>
</div>

<div id="map"></div>

<div id="deleteModal" class="modal hide fade alert alert-error alert-block">
    <button type="button" class="close" data-dismiss="modal">&times;</button>
    <p>
        <strong>Удалить?</strong> Вот так просто взять и удалить весь нажитый опыт в области доступной еды и больше не
        делиться ни с кем этим сакральным знанием?
    </p>
    <p>
        <button id="deleteMarkerButtonModal" data-loading-text="Удаляем..." class="btn btn-danger" href="#">Ага</button>
        <button class="btn" data-dismiss="modal">Отмена</button>
    </p>
</div>

<div class="navbar navbar-fixed-bottom navbar-inverse">
    <div class="navbar-inner">
        <ul class="nav">
            <li><a href="mailto:popov.petr@gmail.com">Разработка и поддержка - Петр Попов, &copy; 2013</a></li>
        </ul>

        <form id="currentActionForm" class="navbar-form form-inline centerForm" style="display: none;">
            <label class="blackLabel">
                <h4 id="currentActionText">Добавление новой точки</h4>
            </label>
            <button id="cancelCurrentAction" class="btn">Отмена</button>
        </form>
    </div>
</div>

</body>
</html>