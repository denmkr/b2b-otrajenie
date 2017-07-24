<#ftl encoding="utf-8">
<#setting locale="ru_RU">

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<!-- Header -->
<#include "modules/header.ftl">

    <!-- Main panel -->
    <div class="main cartpage">
        <div class="cart_container">
        <#include "ajax/cart_content.ftl">
        </div>
    </div>

<#include "modules/footer.ftl">





