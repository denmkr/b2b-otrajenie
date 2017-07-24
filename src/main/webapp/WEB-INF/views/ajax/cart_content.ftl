<#ftl encoding="utf-8">
<#setting locale="ru_RU">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<#if cart.cartProducts??>
    <#if cart.size!=0>
        <div class="table_panel">
            <div class="inside">
                <table class="cart_table catalog_table">
                    <thead>
                        <th><a>Артикул</a><i class="mdi mdi-chevron-down"></i></th>
                        <th><a>Название</a><i class="mdi mdi-chevron-down"></i></th>
                        <th><a>Цена</a><i class="mdi mdi-chevron-down"></i></th>
                        <th><a>Количество</a><i class="mdi mdi-chevron-down"></i></th>
                        <th><a>Общая стоимость</a><i class="mdi mdi-chevron-down"></i></th>
                        <th class="cart"><a>Удалить</a></th>
                    </thead>
                    <tbody>
                        <#list cart.cartProducts as cartProduct>
                        <tr>
                            <td><a>${cartProduct.product.articule}</a></td>
                            <td><a style="color: #618ce5; cursor: pointer;" onclick="getProduct('${cartProduct.product.articule}');">${cartProduct.product.name}</a></td>
                            <@security.authorize access="hasRole('ROLE_USER')">
                                <td><a>${cartProduct.product.retailPrice?string["0.##########"]}</a> ${cartProduct.product.currency}</td>
                            </@security.authorize>
                            <@security.authorize access="hasRole('ROLE_PARTNER')">
                                <td><a>${cartProduct.product.wholesalePrice?string["0.##########"]}</a> ${cartProduct.product.currency}</td>
                            </@security.authorize>
                            <@security.authorize access="isAnonymous()">
                                <td><a>${cartProduct.product.retailPrice?string["0.##########"]}</a> ${cartProduct.product.currency}</td>
                            </@security.authorize>
                            <@security.authorize access="hasRole('ROLE_ADMIN')">
                                <td><a>${cartProduct.product.retailPrice?string["0.##########"]}</a> ${cartProduct.product.currency}</td>
                            </@security.authorize>
                            <td class="cart">
                                <form id="cart_form_set" class="cart_form">
                                <input name="amount" maxlength="2" type="text" placeholder="0" value="${cartProduct.count}"/>
                                <div class="buttons">
                                    <div onclick="addOneProductIntoCart('${cartProduct.product.articule}')" class="plus"><i class="mdi mdi-chevron-up"></i></div>
                                    <div onclick="removeOneProductFromCart('${cartProduct.product.articule}')" class="minus"><i class="mdi mdi-chevron-down"></i></div>
                                </div>
                                </form>
                            </td>
                            <@security.authorize access="hasRole('ROLE_USER')">
                                <td><a>${(cartProduct.count * cartProduct.product.retailPrice)?string["0.##########"]}</a> ${cartProduct.product.currency}</td>
                            </@security.authorize>
                            <@security.authorize access="hasRole('ROLE_PARTNER')">
                                <td><a>${(cartProduct.count * cartProduct.product.wholesalePrice)?string["0.##########"]}</a> ${cartProduct.product.currency}</td>
                            </@security.authorize>
                            <@security.authorize access="isAnonymous()">
                                <td><a>${(cartProduct.count * cartProduct.product.retailPrice)?string["0.##########"]}</a> ${cartProduct.product.currency}</td>
                            </@security.authorize>
                            <@security.authorize access="hasRole('ROLE_ADMIN')">
                                <td><a>${(cartProduct.count * cartProduct.product.retailPrice)?string["0.##########"]}</a> ${cartProduct.product.currency}</td>
                            </@security.authorize>
                            <td class="remove">
                                <div class="button" onclick="removeProductsFromCart('${cartProduct.product.articule}')"><i class="mdi mdi-close"></i></div>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>

                <div class="cart_bottom">
                    <div class="inside">
                        <div class="row middle">
                            <div class="col-4 left">
                                <div class="removeall" onclick="removeAllProductsFromCart()"><a>Удалить все</a></div>
                                <div class="amount">В корзине: ${cart.size}</div>
                            </div>
                            <div class="col-8 right">
                                <div class="sum"><a>Итого ${cart.price?string["0.##########"]}</a> руб.</div>
                                <a href="/placeorder/email">
                                    <div class="button">
                                        <span>Оформить заказ</span>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
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
