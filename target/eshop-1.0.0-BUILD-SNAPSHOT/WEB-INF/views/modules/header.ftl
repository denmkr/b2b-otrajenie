<#ftl encoding="utf-8">
<#setting locale="ru_RU">

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>Интернет-магазин</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/normalize.css" />
    <link href="/resources/css/animate.css" rel="stylesheet" />
    <link href="/resources/css/balloon.min.css" rel="stylesheet" />
    <link href="/resources/css/pretty.min.css" rel="stylesheet" />
    <link href="/resources/css/materialdesignicons.min.css" media="all" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="/resources/css/cs-select.css" />
    <link rel="stylesheet" type="text/css" href="/resources/css/cs-skin-border.css" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <link rel="stylesheet" type="text/css" href="/resources/css/font-awesome.min.css" />
    <link href="/resources/css/style2.css" rel="stylesheet" />

    <script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"></script>
    <script src="/resources/js/selectFx.js"></script>
    <script src="/resources/js/classie.js"></script>

    <script>
        token = "${_csrf.token}";
        header = "${_csrf.headerName}";
    </script>

    <script type="text/javascript" src="/resources/js/javascript2.js"></script>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>

<!-- Modal window -->
<div class="modal_container">
    <img src="/resources/images/close.png">
</div>
<div class="modal">
</div>
<div class="progress-bar"></div>

<!-- Header -->
<div class="header-container">
    <header>
        <div class="inside">
            <div class="row">
                <div class="col-2 left">
                    <div class="logo_container">
                        <div class="logo">
                            <img src="/resources/images/logo.png">
                        </div>
                    </div>
                </div>
                <div class="col-8 center">
                    <ul class="main_menu">
                        <li>
                            <a href="/main">Основной сайт</a>
                        </li>
                        <li class="current">
                            <a href="/catalog">Каталог</a>
                        </li>
                        <li>
                            <a href="/cart">Корзина</a>
                            <div class="amount"><span><#include "../ajax/cart_size.ftl"></span></div>
                        </li>
                    </ul>
                </div>
                <div class="col-2 right">
                    <div class="image">
                        <i class="mdi mdi-account-outline"></i>
                    </div>
                    <@security.authorize access="isAuthenticated()">
                        <div class="user">
                            <div class="user_text">${username.username}</div>
                            <img class="user_img" src="/resources/images/user_down.png">
                            <div class="user_menu">
                                <ul>
                                    <li>Личный кабинет</li>
                                    <a href="/logout"><li>Выйти</li></a>
                                </ul>
                            </div>
                        </div>
                    </@security.authorize>
                    <@security.authorize access="isAnonymous()">
                        <a href="/signin">
                            <div class="user">
                                <div class="user_text">Войти</div>
                            </div>
                        </a>
                    </@security.authorize>
                </div>
            </div>
        </div>
    </header>
</div>