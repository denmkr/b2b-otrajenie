<#ftl encoding="utf-8">
<#setting locale="ru_RU">

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<#include "modules/header.ftl">

<!-- Шапка -->
<header>
    <div class="inside">
        <div class="logo">
            <div class="text">Отражение</div>
        </div>
        <ul class="menu">
            <a href="/"><li>Главная</li></a>
            <a href="/catalog"><li>Каталог</li></a>
            <a href="/shipping"><li>Доставка</li></a>
            <a href="/contacts"><li>Контакты</li></a>
        </ul>
    </div>
</header>

<!-- Sidebar -->
<#include "modules/aside.ftl">

<!-- Главный блок -->
<div class="main">
    <div class="back catalog" data-stellar-background-ratio="0.5">
        <div class="title">Товар</div>
    </div>
    <div class="inside">
        <div class="content page">
            <div class="picture">
                <img style="width: 100%;" src="/resources/images/no-image.png">
            </div>
            <div class="information">
                <div class="group"><#if product.productGroup.parentGroup.parentGroup??>${product.productGroup.parentGroup.parentGroup.name}</#if> <#if product.productGroup.parentGroup??>${product.productGroup.parentGroup.name}</#if> ${product.productGroup.name}</div>
                <div class="name">${product.name}</div>
                <div class="price">${product.price} ${product.currency}.</div>
                <div onclick="addToCart('${product.articule}')" class="addtocart">Добавить в корзину</div>
                <div class="add_information">
                    <div class="stock">
                        <img src="/resources/images/stock.png">
                        <div class="title">На складе</div>
                        <div class="text">${product.stock}</div>
                    </div>
                    <div class="articule">
                        <img src="/resources/images/price.png">
                        <div class="title">Артикул</div>
                        <div class="text">${product.articule}</div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<#include "modules/footer.ftl">