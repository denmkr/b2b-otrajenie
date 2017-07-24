<#ftl encoding="utf-8">
<#setting locale="ru_RU">

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<!-- Header -->
<#include "modules/header.ftl">

<div class="main errorpage">

    <div class="error">
        <div class="head">404</div>
        <div class="sub">К сожалению данной страницы не существует</div>
        <div class="button" onclick="window.history.back();">Вернуться назад</div>
    </div>

</div>

<!-- Footer -->
<#include "modules/footer.ftl">

