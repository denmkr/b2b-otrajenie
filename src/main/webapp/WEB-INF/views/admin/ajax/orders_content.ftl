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
        <th>Номер заказа</th>
        <th>Пользователь</th>
        <th>Товары</th>
        <th>Дата</th>
        </thead>
        <tbody>
        <#list orders as order>
        <tr>
            <td><a><#if order.id??>${order.id}</#if></a></td>
            <td><a><#if order.user??>${order.user.username}</#if></a></td>
            <td><a>
            <#list order.orderProducts as orderProduct>
                <p>${orderProduct.product.name} (Количество: ${orderProduct.quantity}, цена одного товара: <#if orderProduct.price??>${orderProduct.price}</#if>) </p>
                <hr>
             </#list>
            </a></td>
            <td><a>${order.date}</a></td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>



