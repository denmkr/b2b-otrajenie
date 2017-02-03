<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<header>
    <div class="user">
        <div class="text">
            Заказы
        </div>
    </div>

</header>

<div class="table_panel">
    <table class="table">
        <thead>
        <th>Username</th>
        <th>Наименование</th>
        <th>Количество</th>
        <th>Дата</th>
        </thead>
        <tbody>
        <#list orders as order>
        <tr>
            <td><a><#if order.user??>${order.user.username}</#if></a></td>
            <td><a><#if order.product??>${order.product.name}</#if></a></td>
            <td><a>${order.count}</a></td>
            <td><a>${order.date}</a></td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>



