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
<div class="auth auth-sign">
    <div class="auth-container auth-container-sign">
    </div>
    <div style="margin-top: -450px;" class="auth-content auth-content-sign">
        <div class="logo animated fadeInDown">
            <div class="head">Отражение</div>
            <div class="sub">интернет-магазин</div>
        </div>
        <div class="modal animated flipInXSmall">
            <div class="title">Регистрация</div>
            <@form.form commandName="signup_form" action="/signup" acceptCharset="UTF-8"  method="post">
                <@form.input name="username" path="username" class="form-control" placeholder="Логин" />
                <div><@form.errors path="username" cssStyle="color: #a71f1f;" /></div>

                <@form.input path="email" name="email" class="form-control" placeholder="Email" />
                <div><@form.errors path="email" cssStyle="color: #a71f1f;" /></div>

                <@form.input path="password" type="password" name="password" class="form-control" placeholder="Пароль" />
                <div><@form.errors path="password" cssStyle="color: #a71f1f;" /></div>

                <@form.input path="confirmPassword" type="password" name="confirmPassword" class="form-control" placeholder="Подтверждение пароля" />
                <div><@form.errors path="confirmPassword" cssStyle="color: #a71f1f;" /></div>

                <div class="button" onclick="document.getElementById('signup_form').submit();">Зарегистрироваться</div>
            </@form.form>
            <div class="reg">
                Уже зарегистрированы? <a href="/signin">Войти</a>
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
