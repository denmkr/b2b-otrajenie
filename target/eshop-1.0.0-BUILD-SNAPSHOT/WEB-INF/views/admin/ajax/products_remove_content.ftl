<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<header>
    <div class="user">
        <div class="text">
            Удаление товаров
        </div>
    </div>

</header>

<div class="table_panel">
    <@form.form method="POST" style="margin: 0 3%;text-align: left;" action="/admin/products/removeProduct">
        <input type="text" name="articule" placeholder="Артикул товара"><br />
        <input style="display: block;font-size: 16px;
padding: 10px 20px;
background: #3f4755;
color: #fff;cursor: pointer;
margin-top: 60px;" type="submit" value="Удалить товар">
    </@form.form>
</div>

