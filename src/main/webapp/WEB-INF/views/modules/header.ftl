<#ftl encoding="utf-8">

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>Интернет-магазин</title>
    <link href="/resources/css/animate.css" rel="stylesheet"/>
    <link href="/resources/css/style2.css" rel="stylesheet"/>
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,700&subset=latin,cyrillic-ext,cyrillic'
          rel='stylesheet' type='text/css'>
    <script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.sticky.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.stellar.js"></script>
    <script type="text/javascript" src="/resources/js/javascript.js"></script>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>

<div class="wrapper">
    <div class="wrapper-content">

        <!-- Панель покупателя -->
        <div class="shop-header-container">
            <div class="shop-header">
                <div class="inside">
                <@security.authorize access="isAuthenticated()">
                    <a href="/logout">
                        <div class="logout">
                            <img src="/resources/images/logout.png">
                            <span class="title">Выйти</span>
                        </div>
                    </a>
                </@security.authorize>
                <@security.authorize access="hasRole('ROLE_ADMIN')">
                    <a href="/admin">
                        <div class="admin">
                            <img src="/resources/images/admin.png">
                            <span class="title">Панель администратора</span>
                        </div>
                    </a>
                </@security.authorize>
                    <a href="/cart">
                        <div class="cart">
                            <img src="/resources/images/cart.png">
                        <span class="size">
                        <#include "../ajax/cart_size.ftl">
                        </span>
                            <span class="title">Корзина</span>
                        </div>
                    </a>
                <@security.authorize access="isAnonymous()">
                    <a href="/signin">
                        <div class="user">
                            <img src="/resources/images/user.png">
                            <span>Войти</span>
                        </div>
                    </a>
                </@security.authorize>
                <@security.authorize access="isAuthenticated()">
                    <div class="user">
                        <img src="/resources/images/user.png">
                        <a>${username.username}</a>
                    </div>
                </@security.authorize>

                </div>
            </div>
        </div>
