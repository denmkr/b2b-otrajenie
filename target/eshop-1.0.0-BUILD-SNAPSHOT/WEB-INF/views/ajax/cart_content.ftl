<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<div class="panel_container">
    <div class="panel">
        <div class="filter">
            <div class="first-column">
                <ul class="breadcrumbs">
                    <li><a style="font-size: 22px;font-weight: 300;" href="/cart">Корзина</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<#if cart.cartProducts??>
    <#if cart.size!=0>
        <div class="table_panel">
            <div class="inside">
                <div class="info">В корзине товаров: <a>${cart.size}</a></div>
                <table class="cart_table">
                    <thead>
                    <th>Артикул</th>
                    <th>Название</th>
                    <th>Цена</th>
                    <th>Количество</th>
                    <th>Общая стоимость</th>
                    <th>Удалить</th>
                    </thead>
                    <tbody>
                        <#list cart.cartProducts as cartProduct>
                        <tr>
                            <td><a>${cartProduct.product.articule}</a></td>
                            <td><a style="color: #4e7fa9; cursor: pointer;" onclick="getProduct('${cartProduct.product.articule}');">${cartProduct.product.name}</a></td>
                            <@security.authorize access="hasRole('ROLE_USER')">
                                <td><a>${cartProduct.product.retailPrice}</a> ${cartProduct.product.currency}</td>
                            </@security.authorize>
                            <@security.authorize access="hasRole('ROLE_PARTNER')">
                                <td><a>${cartProduct.product.wholesalePrice}</a> ${cartProduct.product.currency}</td>
                            </@security.authorize>
                            <@security.authorize access="isAnonymous()">
                                <td><a>${cartProduct.product.retailPrice}</a> ${cartProduct.product.currency}</td>
                            </@security.authorize>
                            <td class="cart">
                                <input name="amount" maxlength="3" type="text" onkeyup="if(event.keyCode == 13) setProductAmountInCart('${cartProduct.product.articule}', $(this).val())" value="${cartProduct.count}"/>
                                <div class="buttons">
                                    <div onclick="addOneProductIntoCart('${cartProduct.product.articule}')" class="plus"><img src="/resources/images/user_down.png"></div>
                                    <div onclick="removeOneProductFromCart('${cartProduct.product.articule}')" class="minus"><img src="/resources/images/user_down.png"></div>
                                </div>
                            </td>
                            <@security.authorize access="hasRole('ROLE_USER')">
                                <td><a>${cartProduct.count * cartProduct.product.retailPrice}</a> ${cartProduct.product.currency}</td>
                            </@security.authorize>
                            <@security.authorize access="hasRole('ROLE_PARTNER')">
                                <td><a>${cartProduct.count * cartProduct.product.wholesalePrice}</a> ${cartProduct.product.currency}</td>
                            </@security.authorize>
                            <@security.authorize access="isAnonymous()">
                                <td><a>${cartProduct.count * cartProduct.product.retailPrice}</a> ${cartProduct.product.currency}</td>
                            </@security.authorize>
                            <td onclick="removeProductsFromCart('${cartProduct.product.articule}')" class="into_cart">
                                <a style="cursor:pointer;">Удалить</a>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>

                <table class="cart_table bottom">
                    <tbody>
                    <tr>
                        <td><a>Общее</a></td>
                        <td><a></a></td>
                        <td><a></a></td>
                        <td><a>${cart.size}</a>
                        </td>
                        <td><a style="font-size: 19px;">${cart.price}</a> руб.</td>
                        <td onclick="removeAllProductsFromCart()"><a style="cursor: pointer;">Удалить все</a></td>
                    </tr>
                    </tbody>
                </table>

                <a href="/placeorder/email"><div style="margin-top: 100px;
font-size: 1.1rem;
color: #3c6689;
width: 200px;
display: block;

border: 1px solid #3c6689;
transition: all 0.3s ease-out;"><span style="display: inline-block;
vertical-align: middle;
padding: 14px 20px;
color: #3c6689;">Оформить заказ</span><img style="display: inline-block;
vertical-align: middle;
padding: 14px;
width: 20px;
border-left: 1px solid #3c6689;
display: none;" src="/resources/images/right-arrow.png"</div></a>
            </div>
        </div>
    <#else>
    <div class="inside">
        <div style="text-align: left;">
            <div style="font-size: 24px;color: #666;margin-top: 40px;">Ваша корзина пустая</div>
            <div style="display:none; margin-top: 50px;"><img style="width: 200px;" src="/resources/images/emptycart.png"></div>

            <a href="/catalog"><div style="
    margin-top: 100px;
    font-size: 1.1rem;
    color: #3c6689;
    padding: 20px 50px;
    display: inline-block;
    border: 1px solid #3c6689;
    transition: all 0.3s ease-out;
    cursor: pointer;">Перейти к каталогу</div></a>
        </div>
    </div>
    </#if>
</#if>
