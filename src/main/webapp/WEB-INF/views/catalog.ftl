<#ftl encoding="utf-8">
<#setting locale="ru_RU">

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<!-- Header -->
<#include "modules/header.ftl">

<!-- Sidebar -->
<#include "modules/aside.ftl">

<!-- Main panel -->
<div class="main">

    <div class="catalog_container">
        <div class="panel_container">
            <div class="inside">
                <div class="panel">
                    <div class="row middle">
                        <div class="col-7 left">
                            <div class="breadcrumbs_container">
                                <#include "modules/breadcrumbs.ftl">
                            </div>
                        </div>
                        <div class="col-5 right">
                            <div class="search">
                                <form class="search_form" onkeypress="return event.keyCode != 13;">
                                    <input type="text" name="search" placeholder="Поиск" onkeyup="filterProducts()">
                                    <div class="mode">
                                        <div class="current" rel="cur">Текущий</div>
                                        <div rel="all">Весь</div>
                                    </div>
                                </form>
                            </div>
                            <div class="search_button">
                                <i class="mdi mdi-dots-vertical"></i>
                            </div>
                            <div class="filter_button">
                                <i class="mdi mdi-filter"></i>
                                <a>Фильтр</a>
                            </div>
                        </div>
                    </div>
                    <div class="filter">
                        <div class="row middle">
                            <div class="col-12 right">
                                <form class="filter_form">
                                    <div class="stock">
                                        <div class="pretty success circle smooth">
                                            <input onchange="filterProducts()" id="check" name="stock" type="checkbox">
                                            <label><i class="mdi mdi-check"></i>В наличии</label>
                                        </div>
                                    </div>
                                    <div class="order">
                                        <a>Сортировка</a>
                                        <select name="sort" id="select_sort" class="cs-select cs-skin-rotate">
                                            <option value="name_ASC">По наименованию (возр.)</option>
                                            <option value="name_DESC">По наименованию (убыв.)</option>
                                            <option value="retailPrice_ASC">По цене (возр.)</option>
                                            <option value="retailPrice_DESC">По цене (убыв.)</option>
                                            <option value="stock_ASC">По наличию (возр.)</option>
                                            <option value="stock_DESC">По наличию (убыв.)</option>
                                            <option value="articule_ASC">По артикулу (возр.)</option>
                                            <option value="articule_DESC">По артикулу (убыв.)</option>
                                        </select>
                                    </div>
                                </form>
                                <div class="close_button">
                                    <i class="mdi mdi-close"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="table_panel">
            <div class="inside">
                <#include "ajax/catalog_content.ftl">
            </div>
        </div>

    </div>

</div>

<#include "modules/footer.ftl">