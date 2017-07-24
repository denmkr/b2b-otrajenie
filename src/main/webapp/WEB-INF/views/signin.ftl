<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
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
<div class="auth auth-sign">
    <div class="auth-container auth-container-sign">
    </div>
    <div class="auth-content auth-content-sign">
        <div class="logo animated fadeInDown">
            <div class="head">Отражение</div>
            <div class="sub">интернет-магазин</div>
        </div>
        <div class="modal animated flipInXSmall">
            <div class="title">Авторизация</div>
            <@form.form id="signin_form" action="j_spring_security_check" acceptCharset="UTF-8" method="POST">
                <input type="text" name="j_username" placeholder="Логин">
                <input type="password" name="j_password" placeholder="Пароль">
                <input style="display: none;" type="checkbox" name="remember-me" checked="checked" />
                <div class="button" onkeydown="" onclick="document.getElementById('signin_form').submit();">Войти</div>
            </@form.form>
            <#if error??>
                <div class="wrong_data">
                    Неправильный логин или пароль
                </div>
            </#if>
            <div class="reg">
                Впервые у нас? <a href="/signup">Зарегистрируйтесь</a>
            </div>
        </div>
        <a href="/">
            <div class="close animated fadeInUp">
                <img src="/resources/images/back_button.png">
                <div>Вернуться на главную</div>
            </div>
        </a>
    </div>
</div>


</body>
</html>
