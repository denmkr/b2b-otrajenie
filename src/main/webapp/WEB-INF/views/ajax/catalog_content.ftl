<#ftl encoding="utf-8">
<#setting locale="ru_RU">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<#if products.totalElements!=0>
<div class="table_space"></div>
<table class="catalog_table">
    <thead>
    <th class="articule"><a>Артикул</a><i class="mdi mdi-chevron-down"></i></th>
    <th class="name"><a>Название</a><i class="mdi mdi-chevron-down"></i></th>
    <th class="stock"><a>Наличие</a><i class="mdi mdi-chevron-down"></i></th>
    <th class="price"><a>Цена</a><i class="mdi mdi-chevron-down"></i></th>
    <th class="cart"><a>В корзину</a></th>
    </thead>

    <tbody>
        <#list products.content as product>
        <tr>
            <td><a>${product.articule}</a></td>
            <td><a style="color: #618ce5; cursor: pointer" onclick="getProduct('${product.articule}');">${product.name}</a></td>
            <#if (product.stock < 1)>
                <td class="stock_td">
                    <div class="level no">
                    </div>
                    <a>Нет</a>
                </td>
            </#if>
            <#if (product.stock < 3) && (product.stock >= 1)>
                <td class="stock_td">
                    <div class="level small">
                    </div>
                    <a>Мало</a>
                </td>
            </#if>
            <#if (product.stock < 8) && (product.stock >= 3)>
                <td class="stock_td">
                    <div class="level medium">
                    </div>
                    <a>Средне</a>
                </td>
            </#if>
            <#if (product.stock >= 8)>
                <td class="stock_td">
                    <div class="level lots">
                    </div>
                    <a>Много</a>
                </td>
            </#if>
            <@security.authorize access="hasRole('ROLE_USER')">
                <td><a>${product.retailPrice?string["0.##########"]}</a> ${product.currency}</td>
            </@security.authorize>
            <@security.authorize access="hasRole('ROLE_PARTNER')">
                <td><a>${product.wholesalePrice?string["0.##########"]}</a> ${product.currency}</td>
            </@security.authorize>
            <@security.authorize access="isAnonymous()">
                <td><a>${product.retailPrice?string["0.##########"]}</a> ${product.currency}</td>
            </@security.authorize>
            <@security.authorize access="hasRole('ROLE_ADMIN')">
                <td><a>${product.retailPrice?string["0.##########"]}</a> ${product.currency}</td>
            </@security.authorize>
            <td class="cart">
                <form id="cart_form_add" class="cart_form">
                    <input name="amount" maxlength="2" type="text" placeholder="0" value="1">
                    <div class="buttons">
                        <div class="plus"><i class="mdi mdi-chevron-up"></i></div>
                        <div class="minus"><i class="mdi mdi-chevron-down"></i></div>
                    </div>
                </form>
                <div class="button"><i class="mdi mdi-cart"></i></div>
            </td>

        </tr>
        </#list>
    </tbody>

</table>

<#else>
<div style="text-align: left;font-size: 22px; color: #3f4755;">Товаров не найдено</div>
</#if>

<ul class="paginator">

    <div class="column">
        <div style="line-height: 40px; display: inline-block;" class="info">Всего товаров:  <a>${products.totalElements}</a></div>
        <li class="count">
            <div class="order">
                <form class="count_form">
                    <a>На странице:</a>
                    <div style="display: inline-block; padding-left: 20px;" class="order_form_select_div">
                        <select name="count" onchange="filterProducts()">
                            <option selected="selected" value="20">20</option>
                            <option value="30">30</option>
                            <option value="50">50</option>
                            <option value="100">100</option>
                            <option value="200">200</option>
                        </select>
                    </div>
                </form>
            </div>
        </li>
    </div>
    <div class="column">
        <#if (products.totalPages > 1)>
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
        </#if>
    </div>

</ul>