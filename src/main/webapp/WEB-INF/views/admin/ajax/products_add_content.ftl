<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<header>
    <div class="user">
        <div class="text">
            Добавление товаров
        </div>
    </div>

</header>

<div class="table_panel">
    <@form.form method="POST" style="margin: 0 3%;text-align: left;" action="/admin/products/addProduct">
        <input type="text" name="name" placeholder="Название"><br />
        <input type="text" name="articule" placeholder="Артикул"><br />
        <input type="text" name="stock" placeholder="Количество на складе"><br />
        <input type="text" name="price" placeholder="Цена"><br />
        <input type="text" name="currency" placeholder="Валюта"><br />
        <a style="font-size: 15px;margin-left: 10px;color: #aaa;">Группа</a>
        <select name="groupId" style="font-size: 16px;margin-left: 30px;margin-bottom: 10px;margin-top: 10px;">
        <#list groups as group>
            <option value="${group.id}">${group.name}</option>
        </#list>
        </select>

        <input style="display: block;font-size: 16px;
padding: 10px 20px;
background: #3f4755;
color: #fff;cursor: pointer;
margin-top: 60px;" type="submit" value="Добавить товар">

    </@form.form>
</div>

