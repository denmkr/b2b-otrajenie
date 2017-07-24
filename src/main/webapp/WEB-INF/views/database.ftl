<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<html>
<head>
    <title>Database Updatr Request Page</title>
</head>
<body>

<@form.form method="POST" action="/database">
    <input type="submit" value="UpdateData"> Press here to update database from new files!
</@form.form>

</body>
</html>