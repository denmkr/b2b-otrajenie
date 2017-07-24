var groupId = "";
var mode = "current";

var w, h;

$(document).ready(function () {

    [].slice.call( document.querySelectorAll( 'select.cs-select' ) ).forEach( function(el) {
        new SelectFx(el);
    });

    w = window.innerWidth
        || document.documentElement.clientWidth
        || document.body.clientWidth;

    h = window.innerHeight
        || document.documentElement.clientHeight
        || document.body.clientHeight;

    $(".main .table_panel .catalog_table tbody").height(h-275);


    setCurrentElemSidebar();
    setCurrentStock();
    setCurrentSort();
    setCurrentCount();
    setCurrentSearch();

    $(".main .table_panel .catalog_table td:nth-child(1)").css("width", $('.main .table_panel .catalog_table th:nth-child(1)').css("width"));
    $(".main .table_panel .catalog_table td:nth-child(2)").css("width", $('.main .table_panel .catalog_table th:nth-child(2)').css("width"));
    $(".main .table_panel .catalog_table td:nth-child(3)").css("width", $('.main .table_panel .catalog_table th:nth-child(3)').css("width"));
    $(".main .table_panel .catalog_table td:nth-child(4)").css("width", $('.main .table_panel .catalog_table th:nth-child(4)').css("width"));
    $(".main .table_panel .catalog_table td:nth-child(5)").css("width", $('.main .table_panel .catalog_table th:nth-child(5)').css("width"));


    if (window.location.pathname != "/signin" && window.location.pathname != "/signup" && window.location.pathname != "/admin") {
        $("aside .menu li .elem a").click(function (event) {
            groupId = $(this).attr('rel');

            filterProducts();

            setCurrentBreadcrumbs();

            $("aside .menu li .elem").removeClass('selected');
            $(this).parents(".elem").addClass('selected');
            $(this).parents(".elem").parents(".sub_menu").siblings(".elem").addClass('selected');
            $(this).parents(".elem").parents(".sub_menu").parents(".sub_menu").siblings(".elem").addClass('selected');
            // $(this).parent("li").parent("ul").siblings("ul").children("li").children("div").removeClass('current'); find()

            event.stopPropagation();
            if ($(this).siblings(".menu_icon").hasClass("closed")) {
                $(this).siblings(".menu_icon").removeClass("closed");
                $(this).siblings(".menu_icon").addClass("opened");
                $(this).parents(".elem").siblings(".sub_menu").show("fast");
            }
        });

        $("aside .menu li .elem .menu_icon").click(function (event) {

            $("aside .menu li .elem .menu_icon").removeClass('selected');

            // $(this).parent("li").parent("ul").siblings("ul").children("li").children("div").removeClass('current'); find()

            event.stopPropagation();
            if ($(this).hasClass("opened")) {
                $(this).removeClass("opened");
                $(this).addClass("closed");
                $(this).parents(".elem").siblings(".sub_menu").hide("fast");
            }
            else {
                $(this).removeClass("closed");
                $(this).addClass("opened");
                $(this).parents(".elem").siblings(".sub_menu").show("fast");
            }
        });


    }

    if (window.location.pathname == "/signin" || window.location.pathname == "signup") {
        $('#signin_form').on('keyup', function(e){
            if(e.which == 13) document.getElementById('signin_form').submit();
        });

        $('#signup_form').on('keyup', function(e){
            if(e.which == 13) document.getElementById('signup_form').submit();
        });
    }
});

window.onresize = function(event) {
    w = window.innerWidth
        || document.documentElement.clientWidth
        || document.body.clientWidth;

    h = window.innerHeight
        || document.documentElement.clientHeight
        || document.body.clientHeight;

    $(".main .table_panel .catalog_table tbody").height(h-275);

    $(".main .table_panel .catalog_table td:nth-child(1)").css("width", $('.main .table_panel .catalog_table th:nth-child(1)').css("width"));
    $(".main .table_panel .catalog_table td:nth-child(2)").css("width", $('.main .table_panel .catalog_table th:nth-child(2)').css("width"));
    $(".main .table_panel .catalog_table td:nth-child(3)").css("width", $('.main .table_panel .catalog_table th:nth-child(3)').css("width"));
    $(".main .table_panel .catalog_table td:nth-child(4)").css("width", $('.main .table_panel .catalog_table th:nth-child(4)').css("width"));
    $(".main .table_panel .catalog_table td:nth-child(5)").css("width", $('.main .table_panel .catalog_table th:nth-child(5)').css("width"));

}

$('html').click(function(e) {
    if(!$(e.target).closest('.main .search .mode').length)
        $('.main .search .mode').fadeOut(300);
});

$(document).on("submit", "#cart_form_set", function(e) {
    e.preventDefault();
});
$(document).on("keydown", "#cart_form_set", function(e) {
    if(e.keyCode == 13) {

        var inputValue = $(this).children("input").val();
        var articule = $(this).parents("td.cart").siblings("td:first-child").children("a").text();

        setProductAmountInCart(articule, inputValue);
    }
});


$(document).on("click", ".main .table_panel .catalog_table th:not(.cart)", function() {
    var sort = getParameterByName('sort');

    if ($(this).hasClass("articule")) {

        if (sort==="articule_ASC") {
            $(".cs-select ul li[data-value='articule_DESC']").addClass("cs-selected");
            $(".main .filter .order select.cs-select option").prop("selected", false);
            $(".main .filter .order .cs-select option[value='articule_DESC']").prop("selected", true);
        }
        else {
            $(".cs-select ul li[data-value='articule_ASC']").addClass("cs-selected");
            $(".main .filter .order select.cs-select option").prop("selected", false);
            $(".main .filter .order .cs-select option[value='articule_ASC']").prop("selected", true);
        }

        $(".cs-select ul li").removeClass("cs-selected");

        $(".main .filter .order .cs-placeholder").text($(".cs-select ul li.cs-selected span").text());
    }

    if ($(this).hasClass("name")) {
        if (sort==="name_ASC") {
            $(".cs-select ul li[data-value='name_DESC']").addClass("cs-selected");
            $(".main .filter .order select.cs-select option").prop("selected", false);
            $(".main .filter .order .cs-select option[value='name_DESC']").prop("selected", true);
        }
        else {
            $(".cs-select ul li[data-value='name_ASC']").addClass("cs-selected");
            $(".main .filter .order select.cs-select option").prop("selected", false);
            $(".main .filter .order .cs-select option[value='name_ASC']").prop("selected", true);
        }

        $(".cs-select ul li").removeClass("cs-selected");

        $(".main .filter .order .cs-placeholder").text($(".cs-select ul li.cs-selected span").text());
    }

    if ($(this).hasClass("price")) {
        if (sort==="retailPrice_ASC") {
            $(".cs-select ul li[data-value='retailPrice_DESC']").addClass("cs-selected");
            $(".main .filter .order select.cs-select option").prop("selected", false);
            $(".main .filter .order .cs-select option[value='retailPrice_DESC']").prop("selected", true);
        }
        else {
            $(".cs-select ul li[data-value='retailPrice_ASC']").addClass("cs-selected");
            $(".main .filter .order select.cs-select option").prop("selected", false);
            $(".main .filter .order .cs-select option[value='retailPrice_ASC']").prop("selected", true);
        }

        $(".cs-select ul li").removeClass("cs-selected");

        $(".main .filter .order .cs-placeholder").text($(".cs-select ul li.cs-selected span").text());
    }

    if ($(this).hasClass("stock")) {
        if (sort==="stock_ASC") {
            $(".cs-select ul li[data-value='stock_DESC']").addClass("cs-selected");
            $(".main .filter .order select.cs-select option").prop("selected", false);
            $(".main .filter .order .cs-select option[value='stock_DESC']").prop("selected", true);
        }
        else {
            $(".cs-select ul li[data-value='stock_ASC']").addClass("cs-selected");
            $(".main .filter .order select.cs-select option").prop("selected", false);
            $(".main .filter .order .cs-select option[value='stock_ASC']").prop("selected", true);
        }

        $(".cs-select ul li").removeClass("cs-selected");

        $(".main .filter .order .cs-placeholder").text($(".cs-select ul li.cs-selected span").text());
    }

    filterProducts();
});

$(document).on("submit", "#cart_form_add", function(e) {
    e.preventDefault();
});
$(document).on("keydown", "#cart_form_add", function(e) {
    if(e.keyCode == 13) {

        var thisEl = $(this);

        var inputValue = $(this).children("input").val();

        if (isNaN(inputValue) || inputValue <= 0 || inputValue > 99) {
            $(this).parent("td").addClass("balloon");
        }
        else {

            $(this).siblings(".cart_form").children("input").val("1");

            var articule = $(this).parent("td").siblings("td:first-child").children("a").text();

            $.ajax({
                type: "POST",
                url: "/cart/add",
                data: "articule=" + articule + "&" + "amount=" + inputValue,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                    $("header ul.main_menu li .amount").removeClass("animated flash");
                    $(".progress-bar").addClass("play");
                },
                success: function (msg) {
                    $("header ul.main_menu li .amount span").html(msg);
                    $("header ul.main_menu li .amount").addClass("animated flash");
                    $(".progress-bar").removeClass("play");

                    $(thisEl).siblings(".button").addClass("added");
                    $(thisEl).parents("td").parents("tr").addClass("added");
                    setTimeout(function () {
                        $(thisEl).siblings(".button").removeClass("added");
                        $(thisEl).parents("td").parents("tr").removeClass("added");
                    }, 1000);

                    $(thisEl).children("input").val(1);
                }
            });
        }
    }
});

function filterProducts() {

    var msg = $(".filter_form").serialize();
    var searchMsg = $(".search_form").serialize();
    var countMsg = $(".count_form").serialize();

    var pathArray = window.location.pathname.split('/');

    if (groupId === null) {
        groupId = "";
    }

    var mode1 = mode;
    if (searchMsg == "search=") {
        mode1 = "";
    }

    window.history.pushState(null, null, pathArray[0] + '/' + pathArray[1] + '/' + pathArray[2] + '/1' + "?" + "groupId=" + groupId + "&" + msg + "&" + searchMsg + "&" + "mode=" + mode1 + "&" + countMsg);
    if (pathArray[1] == "cart") document.location.href = pathArray[0] + '/' + 'catalog' + '/' + 'page' + '/1' + "?" + "groupId=" + groupId + "&" + msg + "&" + searchMsg + "&" + "mode=" + mode1 + "&" + countMsg;

    $.ajax({
        type: "GET",
        url: pathArray[0] + '/' + pathArray[1] + '/' + pathArray[2] + '/1',
        data: "ajax=1" + "&" + "groupId=" + groupId + "&" + msg + "&" + searchMsg + "&" + "mode=" + mode1 + "&" + countMsg,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);

            $(".main .table_panel .inside").removeClass("animated fadeIn");
            $(".progress-bar").addClass("play");

        },
        success: function (msg) {
            $(".main .table_panel .inside").html(msg);

            $(".main .table_panel .catalog_table td:nth-child(1)").css("width", $('.main .table_panel .catalog_table th:nth-child(1)').css("width"));
            $(".main .table_panel .catalog_table td:nth-child(2)").css("width", $('.main .table_panel .catalog_table th:nth-child(2)').css("width"));
            $(".main .table_panel .catalog_table td:nth-child(3)").css("width", $('.main .table_panel .catalog_table th:nth-child(3)').css("width"));
            $(".main .table_panel .catalog_table td:nth-child(4)").css("width", $('.main .table_panel .catalog_table th:nth-child(4)').css("width"));
            $(".main .table_panel .catalog_table td:nth-child(5)").css("width", $('.main .table_panel .catalog_table th:nth-child(5)').css("width"));

            $(".main .table_panel .catalog_table tbody").height(h-280);

            $(".main .table_panel .inside").addClass("animated fadeIn");
            $(".progress-bar").removeClass("play");

            setCurrentSort();
            setCurrentCount();
        }
    });

}


function setCurrentElemSidebar() {

    groupId = getParameterByName('groupId');

    if (groupId===null) {
        $("aside .menu .elem").children(".menu_icon").removeClass("opened");
        $("aside .menu .elem").children(".menu_icon").addClass("closed");
        $("aside .menu .elem").removeClass("selected");
    }
    else {
        $('aside .menu li .elem').each(function() {

            if ($(this).children("a").attr('rel') == groupId) {

                $("aside .menu li .elem").removeClass('selected');
                $(this).addClass('selected');
                $(this).parents(".sub_menu").siblings(".elem").addClass('selected');
                $(this).parents(".sub_menu").parents(".sub_menu").siblings(".elem").addClass('selected');

                $(this).addClass("selected");
                $(this).children(".menu_icon").removeClass("closed");
                $(this).children(".menu_icon").addClass("opened");
                $(this).siblings("ul").show(250);

                $(this).parent("li").parents("ul").siblings("ul").show(250);
                $(this).parent("li").parents("ul").show(250);

                $(this).parent("li").parents("ul.sub_menu").children("li").children(".elem").children(".menu_icon").addClass("opened");
                $(this).parent("li").parents("ul.sub_menu").parents("li").children(".elem").children(".menu_icon").addClass("opened");
            }
            else {
                $(this).removeClass("selected");
                $(this).children(".menu_icon").removeClass("opened");
                $(this).children(".menu_icon").addClass("closed");
                $(this).siblings("ul").hide(250);
            }

        });
    }

}

function setCurrentBreadcrumbs() {
    var groupId = getParameterByName('groupId');

    $.ajax({
        type: "GET",
        url: "/catalog/breadcrumbs",
        data: "groupId=" + groupId,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(msg) {
            $(".breadcrumbs_container").html(msg);
        }
    });
}

function setCurrentSearch() {
    var search = getParameterByName('search');

    if (search != null) {
        $(".main .search input").val(search);
    }

    var search = getParameterByName('mode');
    $(".main .search .mode div").removeClass("current");

    if (search == "all") {
        $(".main .search .mode div[rel='all']").addClass("current");
    }
    else {
        $(".main .search .mode div[rel='cur']").addClass("current");
    }
}

function setCurrentStock() {
    var stock = getParameterByName('stock');

    if (stock != null) {

        if (stock === "on") {
            $(".main .panel .filter .stock #check").prop("checked", true);
        }
    }
    else {
        if (stock === "off") {
            $(".main .panel .filter .stock #check").prop("checked", false);
        }
    }
}

function setCurrentSort() {
    var sort = getParameterByName('sort');

    if (sort != null) {

        $(".main .filter .order select.cs-select option").prop("selected", false);
        $(".main .filter .order .cs-select option[value='" + sort + "']").prop("selected", true);

        $(".cs-select ul li").removeClass("cs-selected");

        $(".cs-select ul li[data-value='" + sort + "']").addClass("cs-selected");

        if (sort.indexOf("name") + 1) {
            $(".main .table_panel .catalog_table th.name").addClass("current");
            if (sort.indexOf("ASC") + 1) {
                $(".main .table_panel .catalog_table th.name i").removeClass("mdi-chevron-down");
                $(".main .table_panel .catalog_table th.name i").addClass("mdi-chevron-up");
            }
            if (sort.indexOf("DESC") + 1) {
                $(".main .table_panel .catalog_table th.name i").removeClass("mdi-chevron-up");
                $(".main .table_panel .catalog_table th.name i").addClass("mdi-chevron-down");
            }

        }
        if (sort.indexOf("articule") + 1) {
            $(".main .table_panel .catalog_table th.articule").addClass("current");
            if (sort.indexOf("ASC") + 1) {
                $(".main .table_panel .catalog_table th.articule i").removeClass("mdi-chevron-down");
                $(".main .table_panel .catalog_table th.articule i").addClass("mdi-chevron-up");
            }
            if (sort.indexOf("DESC") + 1) {
                $(".main .table_panel .catalog_table th.articule i").removeClass("mdi-chevron-up");
                $(".main .table_panel .catalog_table th.articule i").addClass("mdi-chevron-down");
            }
        }
        if (sort.indexOf("stock") + 1) {
            $(".main .table_panel .catalog_table th.stock").addClass("current");
            if (sort.indexOf("ASC") + 1) {
                $(".main .table_panel .catalog_table th.stock i").removeClass("mdi-chevron-down");
                $(".main .table_panel .catalog_table th.stock i").addClass("mdi-chevron-up");
            }
            if (sort.indexOf("DESC") + 1) {
                $(".main .table_panel .catalog_table th.stock i").removeClass("mdi-chevron-up");
                $(".main .table_panel .catalog_table th.stock i").addClass("mdi-chevron-down");
            }
        }
        if (sort.indexOf("Price") + 1) {
            $(".main .table_panel .catalog_table th.price").addClass("current");
            if (sort.indexOf("ASC") + 1) {
                $(".main .table_panel .catalog_table th.price i").removeClass("mdi-chevron-down");
                $(".main .table_panel .catalog_table th.price i").addClass("mdi-chevron-up");
            }
            if (sort.indexOf("DESC") + 1) {
                $(".main .table_panel .catalog_table th.price i").removeClass("mdi-chevron-up");
                $(".main .table_panel .catalog_table th.price i").addClass("mdi-chevron-down");
            }
        }

        $(".main .filter .order .cs-placeholder").text($(".cs-select ul li.cs-selected span").text());
    }
}

function setCurrentCount() {
    var count = getParameterByName('count');

    if (count != null) {
        $(".main .table_panel .paginator li.count select option[value='" + count + "']").prop("selected", true);
    }
    else {
        $(".main .table_panel .paginator li.count select option[value='" + "20" + "']").prop("selected", true);
    }
}


$(document).on("click", ".main .panel .filter_button", function() {
    $(".main .panel .filter").fadeToggle(300);
});

$(document).on("click", ".main .panel .close_button", function() {
    $(".main .panel .filter").fadeToggle(300);
});

$(document).on("click", ".main .panel .search_button", function() {
    $(".main .search .mode").fadeToggle(300);
});

$(document).on("click", ".main .search .mode div", function() {
    $(".main .search .mode").fadeToggle(300);
});

$(document).on("click", ".cs-select ul li", function() {
    var index = $(this).index();

    $(".main .filter .order select.cs-select option").prop("selected", false);
    $(".main .filter .order select.cs-select option").eq(index).prop("selected", true);

    filterProducts();

});

$(document).on("keydown", "input[name^=amount]", function(e) {
    // Allow: backspace, delete, tab, escape, enter and .
    if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110]) !== -1 ||
            // Allow: Ctrl+A, Command+A
        (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) ||
            // Allow: home, end, left, right, down, up
        (e.keyCode >= 35 && e.keyCode <= 40)) {
        // let it happen, don't do anything
        return;
    }
    // Ensure that it is a number and stop the keypress
    if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
        e.preventDefault();
    }
});

$(document).on("click", ".main .search .mode div:not(.current)", function() {
    $(this).siblings("div").removeClass("current");
    $(this).addClass("current");
});

$(document).on("click", ".main .search .mode div", function() {
    if ($(this).attr("rel") === "cur") mode = "current";
    else mode = "all";

    filterProducts();
});

$(document).on("click", ".main .search_img", function() {
    $(".main").toggleClass("slide");
    $(".main header").toggleClass("slide");
    $("aside .logo").toggleClass("slide");
    $("header .logo").toggle();
});

$(document).on("click", ".modal_container", function() {
    $(".modal").removeClass("showed");
    $(".modal_container").removeClass("showed");
});

$(document).on("click", ".breadcrumbs li", function() {

    groupId = $(this).children("a").attr('rel');
    filterProducts();
    setCurrentElemSidebar();
    setCurrentBreadcrumbs();

});

$(document).on("click", ".modal .content .sub .breadcrumbs li", function() {
    $(".modal").removeClass("showed");
    $(".modal_container").removeClass("showed");
    groupId = $(this).children("a").attr('rel');

    filterProducts();
    setCurrentElemSidebar();
    setCurrentBreadcrumbs();
});

$(document).on("click", ".paginator .column:last-child li", function() {

    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    }
    else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            $(".main .table_panel .inside").html(xmlhttp.responseText);
        }
    }

    xmlhttp.onloadstart = function() {
        $(".main .table_panel .inside").removeClass("animated fadeIn");
        $(".progress-bar").addClass("play");
    }

    xmlhttp.onload = function() {
        $('html, body').animate({
            scrollTop: 0
        }, 400);

        $(".main .table_panel .catalog_table tbody").height(h-280);


        $(".main .table_panel .inside").addClass("animated fadeIn");
        $(".progress-bar").removeClass("play");

        $(".main .table_panel .catalog_table td:nth-child(1)").css("width", $('.main .table_panel .catalog_table th:nth-child(1)').css("width"));
        $(".main .table_panel .catalog_table td:nth-child(2)").css("width", $('.main .table_panel .catalog_table th:nth-child(2)').css("width"));
        $(".main .table_panel .catalog_table td:nth-child(3)").css("width", $('.main .table_panel .catalog_table th:nth-child(3)').css("width"));
        $(".main .table_panel .catalog_table td:nth-child(4)").css("width", $('.main .table_panel .catalog_table th:nth-child(4)').css("width"));
        $(".main .table_panel .catalog_table td:nth-child(5)").css("width", $('.main .table_panel .catalog_table th:nth-child(5)').css("width"));
    }

    var msg = $(".filter_form").serialize();
    var searchMsg = $(".search_form").serialize();
    var countMsg = $(".count_form").serialize();

    if (groupId === null) {
        groupId = "";
    }

    var pathArray = window.location.pathname.split( '/' );

    xmlhttp.open("GET", pathArray[0] + '/' + pathArray[1] + '/' + pathArray[2] + '/' + $(this).attr('rel') + "?ajax=1&" + "groupId=" + groupId + "&" + msg + "&" + searchMsg + "&" + "mode=" + mode + "&" + countMsg, true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlhttp.setRequestHeader(header,token);
    xmlhttp.send("");

    window.history.pushState(null, null, pathArray[0] + '/' + pathArray[1] + '/' + pathArray[2] + '/' + $(this).attr('rel') + "?" + "groupId=" + groupId + "&" + msg + "&" + searchMsg + "&" + "mode=" + mode + "&" + countMsg);

});


/* AJAX функции */

$(document).on("click", "header .user", function() {
    $("header .user .user_menu").toggleClass("show");
});

$(document).mouseup(function (e) {
    var container = $("header .user .user_menu");
    if (container.has(e.target).length === 0){
        container.removeClass("show");
    }
});

$(document).on("click", ".modal .content .cart .button", function() {

    if ($(this).siblings("input").val() === "") {

    }
    else {
        var inputValue = $(this).siblings(".cart_form").children("input").val();
        $(this).siblings(".cart_form").children("input").val("");

        var articule = $(this).parent(".cart").siblings(".info").children(".articule").children(".text").text();

        $.ajax({
            type: "POST",
            url: "/cart/add",
            data: "articule=" + articule + "&" + "amount=" + inputValue,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
                $("header ul.main_menu li .amount").removeClass("animated flash");
                $(".progress-bar").addClass("play");
            },
            success: function (msg) {
                $("header ul.main_menu li .amount span").html(msg);
                $("header ul.main_menu li .amount").addClass("animated flash");
                $(".progress-bar").removeClass("play");

                $(thisEl).addClass("added");
                $(thisEl).parents("td").parents("tr").addClass("added");
                setTimeout(function () {
                    $(thisEl).removeClass("added");
                    $(thisEl).parents("td").parents("tr").removeClass("added");
                }, 1000);
            }
        });

    }

});

$(document).on("click", ".cart .buttons .plus", function() {
    intValue = parseInt($(this).parents(".buttons").siblings("input").val());
    if (isNaN(intValue) || intValue <= 0) intValue = 1;
    else {
        if (intValue >= 99) IntVal = 99;
        else intValue++;
    }

    $(this).parents(".buttons").siblings("input").val(intValue);
});

$(document).on("click", ".cart .buttons .minus", function() {
    intValue = parseInt($(this).parents(".buttons").siblings("input").val());
    if (isNaN(intValue) || intValue>99) intValue = 0;
    else {
        if (intValue <=0) IntVal = 0;
        else intValue--;
    }

    $(this).parents(".buttons").siblings("input").val(intValue);
});


$(document).on("click", ".table_panel .catalog_table tbody td.cart .button", function() {

    var thisEl = $(this);

    intValue = parseInt($(this).siblings(".cart_form").children("input").val());

    if (isNaN(intValue) || intValue <= 0 || intValue > 99) {
        $(this).parent("td").addClass("balloon");
    }
    else {
        var inputValue = $(this).siblings(".cart_form").children("input").val();
        $(this).siblings(".cart_form").children("input").val("1");

        var articule = $(this).parent("td").siblings("td:first-child").children("a").text();

        $.ajax({
            type: "POST",
            url: "/cart/add",
            data: "articule=" + articule + "&" + "amount=" + inputValue,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
                $("header ul.main_menu li .amount").removeClass("animated flash");
                $(".progress-bar").addClass("play");
            },
            success: function (msg) {
                $("header ul.main_menu li .amount span").html(msg);
                $("header ul.main_menu li .amount").addClass("animated flash");
                $(".progress-bar").removeClass("play");

                $(thisEl).addClass("added");
                $(thisEl).parents("td").parents("tr").addClass("added");
                setTimeout(function () {
                    $(thisEl).removeClass("added");
                    $(thisEl).parents("td").parents("tr").removeClass("added");
                }, 1000);

                $(thisEl).siblings("input").val(1);
            }
        });
    }

});

function getProduct(articule) {

    $.ajax({
        type: "GET",
        url: "/product/" + articule,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
            $(".modal_container").addClass("showed");
            $(".modal").addClass("showed");
            $(".progress-bar").addClass("play");
        },
        success: function (msg) {
            $(".modal").html(msg);
            $(".progress-bar").removeClass("play");
        }
    });

}

function setProductAmountInCart(articule, amount) {

    $.ajax({
        type: "POST",
        url: "/cart/set",
        data: "articule=" + articule + "&" + "amount=" + amount,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
            $("header ul.main_menu li .amount").removeClass("animated flash");
            $(".progress-bar").addClass("play");
        },
        success: function (msg) {
            $("header ul.main_menu li .amount span").html(msg);
            $("header ul.main_menu li .amount").addClass("animated flash");
            $(".progress-bar").removeClass("play");

            getCart();
        }
    });

}


function addOneProductIntoCart(articule) {

    $.ajax({
        type: "POST",
        url: "/cart/add",
        data: "articule=" + articule + "&" + "amount=1",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
            $("header ul.main_menu li .amount").removeClass("animated flash");
            $(".progress-bar").addClass("play");
        },
        success: function (msg) {
            $("header ul.main_menu li .amount span").html(msg);
            $("header ul.main_menu li .amount").addClass("animated flash");
            $(".progress-bar").removeClass("play");

            getCart();
        }
    });

}

function removeOneProductFromCart(articule) {

    $.ajax({
        type: "POST",
        url: "/cart/removeone",
        data: "articule=" + articule,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
            $("header ul.main_menu li .amount").removeClass("animated flash");
            $(".progress-bar").addClass("play");
        },
        success: function (msg) {
            $("header ul.main_menu li .amount span").html(msg);
            $("header ul.main_menu li .amount").addClass("animated flash");
            $(".progress-bar").removeClass("play");

            getCart();
        }
    });

}

function removeProductsFromCart(articule) {

    var answer = confirm("Удалить данный товар из корзины?")
    if (answer) {
        $.ajax({
            type: "POST",
            url: "/cart/remove",
            data: "articule=" + articule,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
                $("header ul.main_menu li .amount").removeClass("animated flash");
                $(".progress-bar").addClass("play");
            },
            success: function (msg) {
                $("header ul.main_menu li .amount span").html(msg);
                $("header ul.main_menu li .amount").addClass("animated flash");
                $(".progress-bar").removeClass("play");

                getCart();
            }
        });
    }

}

function removeAllProductsFromCart() {

    var answer = confirm("Удалить все товары из корзины?")
    if (answer) {
        $.ajax({
            type: "POST",
            url: "/cart/removeall",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
                $("header ul.main_menu li .amount").removeClass("animated flash");
                $(".progress-bar").addClass("play");
            },
            success: function (msg) {
                $("header ul.main_menu li .amount span").html(msg);
                $("header ul.main_menu li .amount").addClass("animated flash");
                $(".progress-bar").removeClass("play");

                getCart();
            }
        });
    }

}

function getCart() {

    $.ajax({
        type: "GET",
        url: "/cart",
        data: "ajax=1",
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
            $(".cart_container").removeClass("animated fadeIn");
            $(".progress-bar").addClass("play");
        },
        success: function(msg) {
            $(".cart_container").html(msg);
            $(".cart_container").addClass("animated fadeIn");
            $(".progress-bar").removeClass("play");
        }
    });

}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}