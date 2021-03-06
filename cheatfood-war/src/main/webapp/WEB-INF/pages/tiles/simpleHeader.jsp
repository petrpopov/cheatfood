<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<label id="realPath" hidden="true" style="display: none;">
    <%= request.getScheme()+"://"
            + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>
</label>
<label id="loginLabel" hidden="true" style="display: none;">${login}</label>

<label id="locationLabel" hidden="true" style="display: none;">${location}</label>

<div class="navbar navbar-inverse">
    <div class="navbar-inner">
        <div class="container">

            <a class="rightspacer3" href="<c:url value="/"/>">
                <img src="<s:url value="/resources"/>/img/mainlogo_white_50.png" width="32" style="margin-top: 5px" class="pull-left"/>
            </a>

            <a class="brand spacer3" href="<c:url value="/"/>">
                cheat food
            </a>

            <ul class="nav">
                <li><a href="<s:url value="/manifest"/>">Манифест</a></li>
                <li><a href="<s:url value="/help"/>">Справка</a></li>
            </ul>

            <div class="nav-collapse collapse pull-right">
                <script type="text/javascript">(function() {
                    if (window.pluso)if (typeof window.pluso.start == "function") return;
                    var d = document, s = d.createElement('script'), g = 'getElementsByTagName';
                    s.type = 'text/javascript'; s.charset='UTF-8'; s.async = true;
                    s.src = ('https:' == window.location.protocol ? 'https' : 'http')  + '://share.pluso.ru/pluso-like.js';
                    var h=d[g]('head')[0] || d[g]('body')[0];
                    h.appendChild(s);
                })();</script>
                <div class="pluso pull-right" data-options="small,square,line,horizontal,counter,theme=03"
                     data-services="facebook,twitter,vkontakte,google,odnoklassniki,moimir,email"
                     data-background="transparent" data-url="http://www.cheatfood.com"
                     data-title="Cheat Food" data-description="Клевый сервис для поиска мест, где можно ну очень дешево поесть!"
                     data-user="1262715342"
                     data-image="<s:url value="/resources"/>/img/mainlogo.png"></div>
            </div>

        </div>
    </div>
</div>