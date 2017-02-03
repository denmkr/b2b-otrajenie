<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<div class="photo">
    <img src="/resources/images/no-image.png">
</div>
<div class="content">
    <div class="sub">
        <div class="breadcrumbs">
            <#if product.productGroup??>
                <#if product.productGroup.parentGroup??>
                    <#if product.productGroup.parentGroup.parentGroup??>
                        <#if product.productGroup.parentGroup.parentGroup.parentGroup??>
                            <#if product.productGroup.parentGroup.parentGroup.parentGroup.parentGroup??>
                                <li><a rel="${product.productGroup.parentGroup.parentGroup.parentGroup.parentGroup.groupId}">${product.productGroup.parentGroup.parentGroup.parentGroup.parentGroup.name}</a></li>
                                <li><img src="/resources/images/right.png"></li>
                            </#if>
                            <li><a rel="${product.productGroup.parentGroup.parentGroup.parentGroup.groupId}">${product.productGroup.parentGroup.parentGroup.parentGroup.name}</a></li>
                            <li><img src="/resources/images/right.png"></li>
                        </#if>
                        <li><a rel="${product.productGroup.parentGroup.parentGroup.groupId}">${product.productGroup.parentGroup.parentGroup.name}</a></li>
                        <li><img src="/resources/images/right.png"></li>
                    </#if>
                    <li><a rel="${product.productGroup.parentGroup.groupId}">${product.productGroup.parentGroup.name}</a></li>
                    <li><img src="/resources/images/right.png"></li>
                </#if>
                <li><a rel="${product.productGroup.groupId}">${product.productGroup.name}</a></li>
            </#if>
        </div>
    </div>
    <div class="head">${product.name}</div>
    <div class="info">
        <div class="articule">
            <div class="name">Артикул</div>
            <div class="text">${product.articule}</div>
        </div>
        <div class="stock">
            <div class="name">На складе</div>
            <div class="text">${product.stock}</div>
        </div>
        <div class="price">
            <div class="name">Цена за шт.</div>
            <@security.authorize access="hasRole('ROLE_USER')">
                <div class="text">${product.retailPrice} ${product.currency}</div>
            </@security.authorize>
            <@security.authorize access="hasRole('ROLE_PARTNER')">
                <div class="text">${product.wholesalePrice} ${product.currency}</div>
            </@security.authorize>
            <@security.authorize access="isAnonymous()">
                <div class="text">${product.retailPrice} ${product.currency}</div>
            </@security.authorize>
        </div>
    </div>
    <div class="cart">
        <form class="cart_form">
            <input name="amount" maxlength="2" type="text" placeholder="0">
            <div class="buttons">
                <div class="plus"><img src="/resources/images/user_down.png"></div>
                <div class="minus"><img src="/resources/images/user_down.png"></div>
            </div>
        </form>
        <div class="button">Добавить в корзину
        </div>
    </div>
</div>