<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<header>
    <div class="user">
        <div class="text">
            Загрузка товаров
        </div>
    </div>

</header>

<div class="table_panel">
    <@form.form method="POST" style="margin: 0 3%;text-align: left;" action="uploadMultipleFile" enctype="multipart/form-data">
        <a style="font-size: 20px;">Файл import.xml:</a> <input type="file" name="file" style="font-size: 16px;margin-left: 30px;margin-bottom: 10px;color: #666;"><br />
        <a style="font-size: 20px;">Файл offers.xml:</a> <input type="file" name="file" style="font-size: 16px;margin-left: 30px;color: #666;"><br />
        <input style="font-size: 16px;
padding: 10px 20px;
background: #3f4755;
color: #fff;
margin-top: 60px;" type="submit" value="Обновить товары">
    </@form.form>
</div>

