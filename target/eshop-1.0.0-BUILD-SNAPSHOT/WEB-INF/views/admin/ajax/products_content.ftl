<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<header>
    <div class="user">
        <div class="text">
            Товары
        </div>
    </div>

</header>

<div class="table_panel">
    <table class="table">
        <thead>
        <th>Артикул</th>
        <th>Наименование</th>
        <th>Наличие</th>
        <th>Цена</th>
        <th>Валюта</th>
        </thead>
        <tbody>
        <#list products as product>
        <tr>
            <td><a>${product.articule}</a></td>
            <td><a>${product.name}</a></td>
            <td><a>${product.stock}</a></td>
            <td><a>${product.retailPrice}</a></td>
            <td><a>${product.currency}</a></td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>

