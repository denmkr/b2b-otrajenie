<#ftl encoding="utf-8">

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>Интернет-магазин</title>
    <link href='https://fonts.googleapis.com/css?family=Lato:400,700,300' rel='stylesheet' type='text/css'>
    <link href="/resources/css/style2.css" rel="stylesheet" />
    <link href="/resources/css/animate.css" rel="stylesheet" />
    <link href="/resources/css/balloon.min.css" rel="stylesheet" />
    <script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="/resources/js/javascript2.js"></script>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>

<!-- Модальное окно -->
<div class="modal_container">
    <img src="/resources/images/close.png">
</div>

<div class="modal">
</div>

<div class="progress-bar"></div>
<!-- Боковая панель -->
<aside>
    <div class="logo_container">
        <div class="logo">
            <div class="logo_text">
                <div class="logo_text_head">Отражение</div>
                <div class="logo_text_sub">интернет-магазин</div>
            </div>
        </div>
    </div>
    <div class="page_container">
        <div class="page">Каталог</div>
    </div>
    <ul class="menu">
        <#list groups as group>
        <li>
            <div class="elem selected">
                <a rel="${group.groupId}">${group.name}</a>
            <#if group.childGroups?size!=0><div class="menu_icon closed"></div></#if>
            </div>
        <#if group.childGroups??>
            <#list group.childGroups as group1>
                <ul class="sub_menu">
                    <li>
                        <div class="elem">
                            <a rel="${group1.groupId}">${group1.name}</a>
                            <#if group1.childGroups?size!=0><div class="menu_icon closed"></div></#if>
                        </div>
                        <#if group1.childGroups??>
                            <#list group1.childGroups as group2>
                                <ul class="sub_menu">
                                    <li>
                                        <div class="elem">
                                            <a rel="${group2.groupId}">${group2.name}</a>
                                            <#if group2.childGroups?size!=0><div class="menu_icon closed"></div></#if>
                                        </div>
                                        <#if group2.childGroups??>
                                            <#list group2.childGroups as group3>
                                                <ul class="sub_menu">
                                                    <li>
                                                        <div class="elem">
                                                            <a rel="${group3.groupId}">${group3.name}</a>
                                                            <#if group3.childGroups?size!=0><div class="menu_icon closed"></div></#if>
                                                        </div>
                                                    </li>
                                                </ul>
                                            </#list>
                                        </#if>
                                    </li>
                                </ul>
                            </#list>
                        </#if>
                    </li>
                </ul>
            </#list>
        </#if>
        </li>
        </#list>
    </ul>
</aside>

<!-- Главная панель -->

<div class="main">
    <!-- Header -->
    <div class="header-container">
        <header>
            <div class="inside"><!--
             --><div class="first-column">
                <div class="logo">
                    <div class="logo_text">
                        <div class="logo_text_head">Отражение</div>
                        <div class="logo_text_sub">интернет-магазин</div>
                    </div>
                </div>
                </div><!--
                --><div class="second-column">
                <div class="search">
                    <form class="search_form" onkeypress="return event.keyCode != 13;">
                        <div class="clear">
                            <img src="/resources/images/close_black.png">
                        </div>
                        <input type="text" name="search" placeholder="Введите название или артикул интересующего вас товара" onclick="filterProducts()" onkeyup="filterProducts()">
                        <!--<img class="more" src="/resources/images/more.png">-->
                        <div class="mode">
                            <div data-balloon="Искать в текущем каталоге" data-balloon-pos="down" class="current" rel="cur">Текущий</div>
                            <div data-balloon="Искать по всему каталогу" data-balloon-pos="down" rel="all">Весь</div>
                        </div>
                    </form>
                </div>
            </div><!--
			 --><div class="third-column">
                <a href="/cart"><div class="cart">
                    <div class="img">
                        <img class="img" src="/resources/images/cart.png"/>
                        <div class="amount"><span><#include "ajax/cart_size.ftl"></span></div>
                    </div>
                    <div class="text"><span><#include "ajax/cart_price.ftl"></span></div>
                </div></a><!--
             --><@security.authorize access="isAuthenticated()"><!--
             --><div class="user">
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
            </div><!--
		 --></div>
        </header>
    </div>

    <div class="catalog_container">
        <#include "ajax/catalog_content.ftl">
    </div>

    <footer>
        <div>© 2016 Отражение
        </div>
    </footer>

</div>
</body>
</html>
