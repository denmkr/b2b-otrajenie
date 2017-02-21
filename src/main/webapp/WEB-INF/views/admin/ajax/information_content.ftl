<#ftl encoding="utf-8">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<header>
    <div class="user">
        <div class="text">
            Информация
        </div>
    </div>
</header>

<div class="table_panel">
    <div class="info_panel">
        <div class="block red">
            <div class="number">${users_count}</div>
            <div class="text">Пользователей</div>
        </div>

        <div class="block green">
            <div class="number">135</div>
            <div class="text">Посетителей сегодня</div>
        </div>

        <div class="block blue">
            <div class="number">
            <#if orders_count??>
                 ${orders_count}
            <#else>
                Нет информации
            </#if>
            </div>
            <div class="text">Заказов сегодня</div>
        </div>

        <div class="block blackblue">
            <div class="number">2</div>
            <div class="text">Заявки на партнерство</div>
        </div>
    </div>
    <div style="margin: 30px 3%;text-align: left;width: 90%;" class="graphic_panel">
        <div style="text-align: left;
font-size: 24px;
margin-top: 60px;
margin-bottom: 20px;
margin-left: 10px;">Статистика заказов</div>
        <canvas id="canvas"></canvas>
    </div>
</div>



