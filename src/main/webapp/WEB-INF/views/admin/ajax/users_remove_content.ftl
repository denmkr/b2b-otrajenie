<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<header>
    <div class="user">
        <div class="text">
            Удаление пользователя
        </div>
    </div>

</header>

<div class="table_panel">
    <@form.form method="POST" style="margin: 0 3%;text-align: left;" action="/admin/users/removeUser">
        <a style="font-size: 15px;margin-left: 10px;color: #aaa;">Пользователь</a>
        <select name="userId" style="font-size: 16px;margin-left: 30px;margin-bottom: 10px;margin-top: 10px;">
        <#list users as user>
            <option value="${user.id}">${user.username}</option>
        </#list>
        </select>
        <input style="display: block;font-size: 16px;
padding: 10px 20px;
background: #3f4755;
color: #fff;cursor: pointer;
margin-top: 60px;" type="submit" value="Удалить пользователя">
    </@form.form>
</div>

