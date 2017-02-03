var groupId = "";
var mode = "current";

$(document).ready(function () {

    setCurrentElemSidebar();
    setCurrentFilters();
    setCurrentSearch();

    if (window.location.pathname != "/signin" && window.location.pathname != "/signup" && window.location.pathname != "/admin") {
        $("aside .menu li .elem a").click(function (event) {
            groupId = $(this).attr('rel');
            filterProducts();

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


function filterProducts() {

    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    }
    else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            $(".catalog_container").html(xmlhttp.responseText);
        }
    }

    xmlhttp.onloadstart = function() {
        $(".catalog_container").removeClass("animated fadeIn");
        $(".progress-bar").addClass("play");
    }

    xmlhttp.onload = function() {
        $('html, body').animate({
            scrollTop: 0
        }, 400);
        $(".catalog_container").addClass("animated fadeIn");
        $(".progress-bar").removeClass("play");

        setCurrentFilters();
    }

    var msg = $(".filter_form").serialize();
    var searchMsg = $(".search_form").serialize();
    var countMsg = $(".count_form").serialize();

    var pathArray = window.location.pathname.split( '/' );

    if (groupId === null) {
        groupId = "";
    }

    var mode1 = mode;

    if (searchMsg == "search=") {
        mode1 = "";
    }

    xmlhttp.open("GET", pathArray[0] + '/' + pathArray[1] + '/' + pathArray[2] + '/1' + "?ajax=1&" + "groupId=" + groupId + "&" + msg + "&" + searchMsg + "&" + "mode=" + mode1 + "&" + countMsg, true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xmlhttp.send("");

    window.history.pushState(null, null, pathArray[0] + '/' + pathArray[1] + '/' + pathArray[2] + '/1' + "?" + "groupId=" + groupId + "&" + msg + "&" + searchMsg + "&" + "mode=" + mode1 + "&" + countMsg);

    if (pathArray[1] == "cart") document.location.href = pathArray[0] + '/' + 'catalog' + '/' + 'page' + '/1' + "?" + "groupId=" + groupId + "&" + msg + "&" + searchMsg + "&" + "mode=" + mode1 + "&" + countMsg;

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

function setCurrentSearch() {
    var search = getParameterByName('search');

    if (search != null) {
        $("header .search input").val(search);
    }

    var search = getParameterByName('mode');
    $("header .search .mode div").removeClass("current");

    if (search == "all") {
        $("header .search .mode div[rel='all']").addClass("current");
    }
    else {
        $("header .search .mode div[rel='cur']").addClass("current");
    }

    if ($("header .search input").val() != "") $("header .search").addClass("write");
    else $("header .search").removeClass("write");

}

function setCurrentFilters() {

    var stock = getParameterByName('stock');
    var sort = getParameterByName('sort');

    if (stock != null) {

       //  $(".main .panel .filter .sort select[value = sort]").
        if (stock === "on") {
            $(".main .panel .filter .stock #check").prop("checked", true);
        }
    }
    else {
        if (stock === "off") {
            $(".main .panel .filter .stock #check").prop("checked", false);
        }
    }

    if (sort != null) {
        $(".main .panel .filter .order select option[value='" + sort + "']").prop("selected", true);
    }

    var count = getParameterByName('count');

    if (count != null) {
        $(".main .table_panel .paginator li.count select option[value='" + count + "']").prop("selected", true);
    }
    else {
        $(".main .table_panel .paginator li.count select option[value='" + "20" + "']").prop("selected", true);
    }

}

$(document).on("click", "header .search.write .clear", function() {
    $("header .search input").val("");
    filterProducts();
    $("header .search").removeClass("write");
    setCurrentSearch();
});

$(document).on("keyup", "input[name^=search]", function(e) {
    if ($(this).val() != "") $("header .search").addClass("write");
    else $("header .search").removeClass("write");
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

$(document).on("click", "header .search .mode div:not(.current)", function() {
    $(this).siblings("div").removeClass("current");
    $(this).addClass("current");
});

$(document).on("click", "header .search .mode div", function() {
    if ($(this).attr("rel") === "cur") mode = "current";
    else mode = "all";

    filterProducts();
});

$(document).on("click", "header .search_img", function() {
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

    setCurrentFilters();

});

$(document).on("click", ".modal .content .sub .breadcrumbs li", function() {
    $(".modal").removeClass("showed");
    $(".modal_container").removeClass("showed");
    groupId = $(this).children("a").attr('rel');
    filterProducts();
    setCurrentFilters();
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
            $(".catalog_container").html(xmlhttp.responseText);
        }
    }

    xmlhttp.onloadstart = function() {
        $(".catalog_container").removeClass("animated fadeIn");
        $(".progress-bar").addClass("play");
    }

    xmlhttp.onload = function() {
        $('html, body').animate({
            scrollTop: 0
        }, 400);

        setCurrentFilters();

        $(".catalog_container").addClass("animated fadeIn");
        $(".progress-bar").removeClass("play");
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

$(document).on("mouseleave", ".table_panel table tbody td.add", function() {
    $(this).siblings("td.cart").removeClass("balloon");
});

$(document).on("click", ".modal .content .cart .button", function() {

    if ($(this).siblings("input").val() === "") {

    }
    else {
        var inputValue = $(this).siblings(".cart_form").children("input").val();
        $(this).siblings(".cart_form").children("input").val("");

        var articule = $(this).parent(".cart").siblings(".info").children(".articule").children(".text").text();

        var xmlhttp;
        if (window.XMLHttpRequest) {
            xmlhttp = new XMLHttpRequest();
        }
        else {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }

        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                $("header .cart .amount span").html(xmlhttp.responseText);
                $("header .cart .amount span").addClass("animated flash");

                var pathArray = window.location.pathname.split( '/' );
                if (pathArray[1]==="cart") getCart();
            }
        }

        xmlhttp.onloadstart = function () {
            $("header .cart .amount span").removeClass("animated flash");
            $(".progress-bar").addClass("play");
        }

        xmlhttp.onloadend = function () {
            var xmlhttp2;
            if (window.XMLHttpRequest) {
                xmlhttp2 = new XMLHttpRequest();
            }
            else {
                xmlhttp2 = new ActiveXObject("Microsoft.XMLHTTP");
            }

            xmlhttp2.onreadystatechange = function () {
                if (xmlhttp2.readyState == 4 && xmlhttp2.status == 200) {
                    $("header .cart .text span").html(xmlhttp2.responseText);
                    $("header .cart .text span").addClass("animated flash");
                }
            }

            xmlhttp2.onloadstart = function () {
                $("header .cart .text span").removeClass("animated flash");
            }

            xmlhttp2.onload = function () {
                $(".progress-bar").removeClass("play");
            }

            xmlhttp2.open("GET", "/cart/getprice", true);
            xmlhttp2.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

            xmlhttp2.send("");
        }

        var msg = "articule=" + articule + "&" + "amount=" + inputValue;

        xmlhttp.open("POST", "/cart/add", true);
        xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

        xmlhttp.send(msg);
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


$(document).on("click", ".table_panel .catalog_table tbody td.add img", function() {

    intValue = parseInt($(this).parent("td").siblings("td.cart").children(".cart_form").children("input").val());

    if (isNaN(intValue) || intValue <= 0 || intValue > 99) {
        $(this).parent("td").siblings("td.cart").addClass("balloon");
    }
    else {
        var inputValue = $(this).parent("td").siblings("td.cart").children(".cart_form").children("input").val();
        $(this).parent("td").siblings("td.cart").children(".cart_form").children("input").val("");

        var articule = $(this).parent("td").siblings("td:first-child").children("a").text();

        var xmlhttp;
        if (window.XMLHttpRequest) {
            xmlhttp = new XMLHttpRequest();
        }
        else {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }

        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                $("header .cart .amount span").html(xmlhttp.responseText);
                $("header .cart .amount span").addClass("animated flash");
            }
        }

        xmlhttp.onloadstart = function () {
            $("header .cart .amount span").removeClass("animated flash");
            $(".progress-bar").addClass("play");
        }

        xmlhttp.onloadend = function () {
            var xmlhttp2;
            if (window.XMLHttpRequest) {
                xmlhttp2 = new XMLHttpRequest();
            }
            else {
                xmlhttp2 = new ActiveXObject("Microsoft.XMLHTTP");
            }

            xmlhttp2.onreadystatechange = function () {
                if (xmlhttp2.readyState == 4 && xmlhttp2.status == 200) {
                    $("header .cart .text span").html(xmlhttp2.responseText);
                    $("header .cart .text span").addClass("animated flash");
                }
            }

            xmlhttp2.onloadstart = function () {
                $("header .cart .text span").removeClass("animated flash");
            }

            xmlhttp2.onload = function () {
                $(".progress-bar").removeClass("play");
            }

            xmlhttp2.open("GET", "/cart/getprice", true);
            xmlhttp2.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

            xmlhttp2.send("");
        }

        var msg = "articule=" + articule + "&" + "amount=" + inputValue;

        xmlhttp.open("POST", "/cart/add", true);
        xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

        xmlhttp.send(msg);
    }

});

function getProduct(articule) {

    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    }
    else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            $(".modal").html(xmlhttp.responseText);
        }
    }

    xmlhttp.onloadstart = function() {
        $(".progress-bar").addClass("play");
    }

    xmlhttp.onload = function() {
        $(".modal_container").addClass("showed");
        $(".modal").addClass("showed");
        $(".progress-bar").removeClass("play");
    }

    xmlhttp.open("GET", "/product/" + articule, true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xmlhttp.send("");

}

function setProductAmountInCart(articule, amount) {

    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    }
    else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            $("header .cart .amount span").html(xmlhttp.responseText);
            $(".shop-header .cart").addClass("animated flash");

            getCart();
        }
    }

    xmlhttp.onloadstart = function() {
        $(".shop-header .cart").removeClass("animated flash");
        $(".progress-bar").addClass("play");
    }

    xmlhttp.onload = function() {
        $(".progress-bar").removeClass("play");
    }

    xmlhttp.onloadend = function () {
        var xmlhttp2;
        if (window.XMLHttpRequest) {
            xmlhttp2 = new XMLHttpRequest();
        }
        else {
            xmlhttp2 = new ActiveXObject("Microsoft.XMLHTTP");
        }

        xmlhttp2.onreadystatechange = function () {
            if (xmlhttp2.readyState == 4 && xmlhttp2.status == 200) {
                $("header .cart .text span").html(xmlhttp2.responseText);
                $("header .cart .text span").addClass("animated flash");
            }
        }

        xmlhttp2.onloadstart = function () {
            $("header .cart .text span").removeClass("animated flash");
        }

        xmlhttp2.onload = function () {
            $(".progress-bar").removeClass("play");
        }

        xmlhttp2.open("GET", "/cart/getprice", true);
        xmlhttp2.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

        xmlhttp2.send("");
    }

    var msg = "articule=" + articule + "&" + "amount=" + amount;

    xmlhttp.open("POST", "/cart/set", true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xmlhttp.send(msg);

}


function addOneProductIntoCart(articule) {

    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    }
    else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            $("header .cart .amount span").html(xmlhttp.responseText);
            $(".shop-header .cart").addClass("animated flash");

            getCart();
        }
    }

    xmlhttp.onloadstart = function() {
        $(".shop-header .cart").removeClass("animated flash");
        $(".progress-bar").addClass("play");
    }

    xmlhttp.onload = function() {
        $(".progress-bar").removeClass("play");
    }

    xmlhttp.onloadend = function () {
        var xmlhttp2;
        if (window.XMLHttpRequest) {
            xmlhttp2 = new XMLHttpRequest();
        }
        else {
            xmlhttp2 = new ActiveXObject("Microsoft.XMLHTTP");
        }

        xmlhttp2.onreadystatechange = function () {
            if (xmlhttp2.readyState == 4 && xmlhttp2.status == 200) {
                $("header .cart .text span").html(xmlhttp2.responseText);
                $("header .cart .text span").addClass("animated flash");
            }
        }

        xmlhttp2.onloadstart = function () {
            $("header .cart .text span").removeClass("animated flash");
        }

        xmlhttp2.onload = function () {
            $(".progress-bar").removeClass("play");
        }

        xmlhttp2.open("GET", "/cart/getprice", true);
        xmlhttp2.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

        xmlhttp2.send("");
    }

    var msg = "articule=" + articule + "&" + "amount=1";

    xmlhttp.open("POST", "/cart/add", true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xmlhttp.send(msg);

}

function removeOneProductFromCart(articule) {

    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    }
    else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            $("header .cart .amount span").html(xmlhttp.responseText);
            $(".shop-header .cart").addClass("animated flash");

            getCart();
        }
    }

    xmlhttp.onloadstart = function() {
        $(".shop-header .cart").removeClass("animated flash");
        $(".progress-bar").addClass("play");
    }

    xmlhttp.onload = function() {
        $(".progress-bar").removeClass("play");
    }

    xmlhttp.onloadend = function () {
        var xmlhttp2;
        if (window.XMLHttpRequest) {
            xmlhttp2 = new XMLHttpRequest();
        }
        else {
            xmlhttp2 = new ActiveXObject("Microsoft.XMLHTTP");
        }

        xmlhttp2.onreadystatechange = function () {
            if (xmlhttp2.readyState == 4 && xmlhttp2.status == 200) {
                $("header .cart .text span").html(xmlhttp2.responseText);
                $("header .cart .text span").addClass("animated flash");
            }
        }

        xmlhttp2.onloadstart = function () {
            $("header .cart .text span").removeClass("animated flash");
        }

        xmlhttp2.onload = function () {
            $(".progress-bar").removeClass("play");
        }

        xmlhttp2.open("GET", "/cart/getprice", true);
        xmlhttp2.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

        xmlhttp2.send("");
    }

    var msg = "articule=" + articule;

    xmlhttp.open("POST", "/cart/removeone", true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xmlhttp.send(msg);

}

function removeProductsFromCart(articule) {

    var answer = confirm("Удалить данный товар из корзины?")
    if (answer) {
        var xmlhttp;
        if (window.XMLHttpRequest) {
            xmlhttp = new XMLHttpRequest();
        }
        else {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }

        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                $("header .cart .amount span").html(xmlhttp.responseText);
                $(".shop-header .cart").addClass("animated flash");

                getCart();
            }
        }

        xmlhttp.onloadstart = function() {
            $(".shop-header .cart").removeClass("animated flash");
            $(".progress-bar").addClass("play");
        }

        xmlhttp.onload = function() {
            $(".progress-bar").removeClass("play");
        }

        xmlhttp.onloadend = function () {
            var xmlhttp2;
            if (window.XMLHttpRequest) {
                xmlhttp2 = new XMLHttpRequest();
            }
            else {
                xmlhttp2 = new ActiveXObject("Microsoft.XMLHTTP");
            }

            xmlhttp2.onreadystatechange = function () {
                if (xmlhttp2.readyState == 4 && xmlhttp2.status == 200) {
                    $("header .cart .text span").html(xmlhttp2.responseText);
                    $("header .cart .text span").addClass("animated flash");
                }
            }

            xmlhttp2.onloadstart = function () {
                $("header .cart .text span").removeClass("animated flash");
            }

            xmlhttp2.onload = function () {
                $(".progress-bar").removeClass("play");
            }

            xmlhttp2.open("GET", "/cart/getprice", true);
            xmlhttp2.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

            xmlhttp2.send("");
        }

        var msg = "articule=" + articule;

        xmlhttp.open("POST", "/cart/remove", true);
        xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

        xmlhttp.send(msg);
    }
    else {
        //some code
    }

}

function removeAllProductsFromCart() {
    var answer = confirm("Удалить все товары из корзины?")
    if (answer) {
        var xmlhttp;
        if (window.XMLHttpRequest) {
            xmlhttp = new XMLHttpRequest();
        }
        else {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }

        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                $("header .cart .amount span").html(xmlhttp.responseText);
                $(".shop-header .cart").addClass("animated flash");

                getCart();
            }
        }

        xmlhttp.onloadstart = function() {
            $(".shop-header .cart").removeClass("animated flash");
            $(".progress-bar").addClass("play");
        }

        xmlhttp.onload = function () {
            $(".progress-bar").removeClass("play");
        }

        xmlhttp.onloadend = function () {
            var xmlhttp2;
            if (window.XMLHttpRequest) {
                xmlhttp2 = new XMLHttpRequest();
            }
            else {
                xmlhttp2 = new ActiveXObject("Microsoft.XMLHTTP");
            }

            xmlhttp2.onreadystatechange = function () {
                if (xmlhttp2.readyState == 4 && xmlhttp2.status == 200) {
                    $("header .cart .text span").html(xmlhttp2.responseText);
                    $("header .cart .text span").addClass("animated flash");
                }
            }

            xmlhttp2.onloadstart = function () {
                $("header .cart .text span").removeClass("animated flash");
            }

            xmlhttp2.onload = function () {
                $(".progress-bar").removeClass("play");
            }

            xmlhttp2.open("GET", "/cart/getprice", true);
            xmlhttp2.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

            xmlhttp2.send("");
        }

        xmlhttp.open("POST", "/cart/removeall", true);
        xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

        xmlhttp.send("");
    }
    else{
        //some code
    }
}

function getCart() {

    var xmlhttp2;
    if (window.XMLHttpRequest) {
        xmlhttp2 = new XMLHttpRequest();
    }
    else {
        xmlhttp2 = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp2.onreadystatechange = function () {
        if (xmlhttp2.readyState == 4 && xmlhttp2.status == 200) {
            $(".cart_container").html(xmlhttp2.responseText);
        }
    }

    xmlhttp2.onloadstart = function() {
        $(".cart_container").removeClass("animated fadeIn");
        $(".progress-bar").addClass("play");
    }

    xmlhttp2.onload = function() {
        $(".cart_container").addClass("animated fadeIn");
        $(".progress-bar").removeClass("play");
    }


    xmlhttp2.open("GET", window.location.pathname + "?ajax=1", true);
    xmlhttp2.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xmlhttp2.send("");

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