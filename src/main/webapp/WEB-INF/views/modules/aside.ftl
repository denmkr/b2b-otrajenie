<#ftl encoding="utf-8">
<#setting locale="ru_RU">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<aside>
    <ul class="menu">
    <#list groups as group>
        <li>
            <div class="elem selected">
                <a rel="${group.groupId}">${group.name}</a>
                <#if group.childGroups?size!=0><div class="menu_icon closed"></div></#if>
            </div>
            <#if group.childGroups??>
                <#list group.childGroups as group1>
                    <ul class="sub_menu">
                        <li>
                            <div class="elem">
                                <a rel="${group1.groupId}">${group1.name}</a>
                                <#if group1.childGroups?size!=0><div class="menu_icon closed"></div></#if>
                            </div>
                            <#if group1.childGroups??>
                                <#list group1.childGroups as group2>
                                    <ul class="sub_menu">
                                        <li>
                                            <div class="elem">
                                                <a rel="${group2.groupId}">${group2.name}</a>
                                                <#if group2.childGroups?size!=0><div class="menu_icon closed"></div></#if>
                                            </div>
                                            <#if group2.childGroups??>
                                                <#list group2.childGroups as group3>
                                                    <ul class="sub_menu">
                                                        <li>
                                                            <div class="elem">
                                                                <a rel="${group3.groupId}">${group3.name}</a>
                                                                <#if group3.childGroups?size!=0><div class="menu_icon closed"></div></#if>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                </#list>
                                            </#if>
                                        </li>
                                    </ul>
                                </#list>
                            </#if>
                        </li>
                    </ul>
                </#list>
            </#if>
        </li>
    </#list>
    </ul>
</aside>