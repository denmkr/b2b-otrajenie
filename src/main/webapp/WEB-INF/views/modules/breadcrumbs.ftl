<#ftl encoding="utf-8">
<#setting locale="ru_RU">
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<ul class="breadcrumbs">
    <li><a rel="">Каталог</a></li>
    <#if currentGroup??>
        <i class="mdi mdi-chevron-right"></i>
        <#if currentGroup.parentGroup??>
            <#if currentGroup.parentGroup.parentGroup??>
                <#if currentGroup.parentGroup.parentGroup.parentGroup??>
                    <#if currentGroup.parentGroup.parentGroup.parentGroup.parentGroup??>
                        <li><a rel="${currentGroup.parentGroup.parentGroup.parentGroup.parentGroup.groupId}">${currentGroup.parentGroup.parentGroup.parentGroup.parentGroup.name}</a></li>
                        <i class="mdi mdi-chevron-right"></i>
                    </#if>
                    <li><a rel="${currentGroup.parentGroup.parentGroup.parentGroup.groupId}">${currentGroup.parentGroup.parentGroup.parentGroup.name}</a></li>
                    <i class="mdi mdi-chevron-right"></i>
                </#if>
                <li><a rel="${currentGroup.parentGroup.parentGroup.groupId}">${currentGroup.parentGroup.parentGroup.name}</a></li>
                <i class="mdi mdi-chevron-right"></i>
            </#if>
            <li><a rel="${currentGroup.parentGroup.groupId}">${currentGroup.parentGroup.name}</a></li>
            <i class="mdi mdi-chevron-right"></i>
        </#if>
        <li><a rel="${currentGroup.groupId}">${currentGroup.name}</a></li>
    </#if>
</ul>