<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<div class="panel_container">

    <div class="panel">

        <div class="filter"><!--
		--><div class="first-column">
            <ul class="breadcrumbs">
                <#if currentGroup??>
                    <#if currentGroup.parentGroup??>
                        <#if currentGroup.parentGroup.parentGroup??>
                            <#if currentGroup.parentGroup.parentGroup.parentGroup??>
                                <#if currentGroup.parentGroup.parentGroup.parentGroup.parentGroup??>
                                    <li><a rel="${currentGroup.parentGroup.parentGroup.parentGroup.parentGroup.groupId}">${currentGroup.parentGroup.parentGroup.parentGroup.parentGroup.name}</a></li>
                                    <li><img src="/resources/images/right.png"></li>
                                </#if>
                                <li><a rel="${currentGroup.parentGroup.parentGroup.parentGroup.groupId}">${currentGroup.parentGroup.parentGroup.parentGroup.name}</a></li>
                                <li><img src="/resources/images/right.png"></li>
                            </#if>
                            <li><a rel="${currentGroup.parentGroup.parentGroup.groupId}">${currentGroup.parentGroup.parentGroup.name}</a></li>
                            <li><img src="/resources/images/right.png"></li>
                        </#if>
                        <li><a rel="${currentGroup.parentGroup.groupId}">${currentGroup.parentGroup.name}</a></li>
                        <li><img src="/resources/images/right.png"></li>
                    </#if>
                    <li><a rel="${currentGroup.groupId}">${currentGroup.name}</a></li>
                <#else>
                    <li><a>Весь каталог</a></li>
                </#if>
            </ul>
        </div><!--
		 --><div class="second-column">
            <form class="filter_form" method="GET">
                <div data-balloon="Только товары в наличии" data-balloon-pos="up" class="stock">
                    <div class="stock_form_checkbox">
                        <input onchange="filterProducts()" id="check" name="stock" class="stock_form_input" type="checkbox">
                        <label for="check">В наличии</label>
                    </div>
                </div>
                <div data-balloon="Сортировка товаров по" data-balloon-pos="up" class="order">
                    <div class="order_form_select_div">
                        <select name="sort" onchange="filterProducts()">
                            <option selected="selected" value="name_ASC">Наименованию (возр.)</option>
                            <option value="name_DESC">Наименованию (убыв.)</option>
                            <option value="retailPrice_ASC">Цене (возр.)</option>
                            <option value="retailPrice_DESC">Цене (убыв.)</option>
                            <option value="stock_ASC">Наличию (возр.)</option>
                            <option value="stock_DESC">Наличию (убыв.)</option>
                            <option value="articule_ASC">Артикулу (возр.)</option>
                            <option value="articule_DESC">Артикулу (убыв.)</option>
                        </select>
                    </div>
                </div>
            </form>
        </div><!--
     --></div>
    </div>
</div>

<div class="table_panel">
    <div class="inside">
    <#if products.totalElements!=0>
        <div class="info">Всего товаров:  <a>${products.totalElements}</a></div>
        <table class="catalog_table">
            <thead>
            <th>Артикул</th>
            <th>Название</th>
            <th>Наличие</th>
            <th>Цена</th>
            <th colspan="3">В корзину</th>
            </thead>

            <tbody>
            <#list products.content as product>
            <tr>
                <td><a>${product.articule}</a></td>
                <td><a style="color: #4e7fa9; cursor: pointer" onclick="getProduct('${product.articule}');">${product.name}</a></td>
                <td><a>${product.stock}</a> шт.</td>
                <@security.authorize access="hasRole('ROLE_USER')">
                    <td><a>${product.retailPrice}</a> ${product.currency}</td>
                </@security.authorize>
                <@security.authorize access="hasRole('ROLE_PARTNER')">
                    <td><a>${product.wholesalePrice}</a> ${product.currency}</td>
                </@security.authorize>
                <@security.authorize access="isAnonymous()">
                    <td><a>${product.retailPrice}</a> ${product.currency}</td>
                </@security.authorize>
                <td data-balloon="Введите кол-во товара" data-balloon-pos="down" class="cart">
                    <@form.form class="cart_form">
                        <input name="amount" maxlength="2" type="text" placeholder="0">
                        <div class="buttons">
                            <div class="plus"><img src="/resources/images/user_down.png"></div>
                            <div class="minus"><img src="/resources/images/user_down.png"></div>
                        </div>
                    </@form.form>
                </td>
                <td data-balloon="Добавить в корзину" data-balloon-pos="up" class="add"><img src="/resources/images/addcart.png"></td>
                <td><a>-</a></td>
            </tr>
            </#list>
            </tbody>

        </table>

        <ul class="paginator">
        <#if products.totalElements != 0>
            <div class="column">
                <li class="count">
                    <div class="order">
                        <form class="count_form">
                            <div class="order_form_select_div">
                                <select name="count" onchange="filterProducts()">
                                    <option value="10">На странице:  10</option>
                                    <option selected="selected" value="20">На странице:  20</option>
                                    <option value="50">На странице:  50</option>
                                    <option value="100">На странице:  100</option>
                                </select>
                            </div>
                        </form>
                    </div>
                </li>
            </div>
            <div class="column">
                <#if beginIndex != currentIndex>
                    <li class="prev" rel="${currentIndex - 1}"><img style="width: 10px;" src="/resources/images/left.png"></li>
                </#if>
                <#list beginIndex..endIndex as i>
                    <#if i == currentIndex>
                        <li rel="${i}" class="active">${i}</li>
                    <#else>
                        <li rel="${i}">${i}</li>
                    </#if>
                </#list>
                <#if products.totalPages != currentIndex>
                    <li class="next" rel="${currentIndex + 1}"><img style="width: 10px;" src="/resources/images/right.png"></li>
                </#if>
            </div>
        </#if>
        </ul>
    <#else>
        <div style="text-align: left;font-size: 22px; color: #3f4755; margin-top: 40px;">Товаров не найдено</div>
    </#if>
    </div>
</div>