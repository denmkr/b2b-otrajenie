<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<head>
    <title>Email Sender</title>
</head>

<@form.form method="POST" action="/placeorder/confirm">
    <input type="text" name="email" placeholder="Введите email">
    <input type="submit" value="Подтвердить">
</@form.form>
