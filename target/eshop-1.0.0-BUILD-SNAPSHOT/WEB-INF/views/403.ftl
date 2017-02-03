<#ftl encoding="utf-8">

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>Интернет-магазин</title>
    <link href="/resources/css/animate.css" rel="stylesheet" />
    <link href="/resources/css/style.css" rel="stylesheet" />
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,700&subset=latin,cyrillic-ext,cyrillic' rel='stylesheet' type='text/css'>
    <script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.sticky.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.stellar.js"></script>
    <script type="text/javascript" src="/resources/js/javascript.js"></script>

</head>
<body>

<!-- Окно авторизации -->
<div class="auth">
    <div class="auth-container">
    </div>
    <div style="margin-top: -238px;" class="auth-content">
        <div class="logo animated fadeInDown">
            <div class="head">Отражение</div>
            <div class="sub">интернет-магазин</div>
        </div>
        <div class="modal animated flipInXSmall">
            <div class="title">Ошибка 403</div>
            <div style="padding-bottom: 30px;">Доступ запрещен</div>
        </div>
        <a href="/">
            <div class="close animated fadeInUp">
                <img src="/resources/images/user.png">
                <div>Вернуться на главную</div>
            </div>
        </a>
    </div>

</div>


</body>
</html>

