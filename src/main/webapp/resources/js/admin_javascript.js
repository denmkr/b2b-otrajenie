var page;

$(document).ready(function () {
    token = $("meta[name='_csrf']").attr("content");
    header = $("meta[name='_csrf_header']").attr("content");

    var pathArray = window.location.pathname.split( '/' );
    if (pathArray[2]=="information" || pathArray[2]==null) getGraphic();

    setCurrentElemSidebar();

    $("aside .menu li").addClass("animated fadeInLeft");

    $("aside .menu li .elem").click(function (event) {

        if ($(this).children(".menu_icon").hasClass("opened")) {
            $(this).children(".menu_icon").removeClass("opened");
            $(this).children(".menu_icon").addClass("closed");

            $(this).siblings(".sub_menu").toggle(250);
        }
        else {
            $(this).children(".menu_icon").removeClass("closed");
            $(this).children(".menu_icon").addClass("opened");

            $(this).siblings(".sub_menu").toggle(250);
        }

        event.stopPropagation();

        $("aside .menu li .elem").removeClass('current');
        $(this).addClass('current');

        page = $(this).attr("rel");
        getPage();

    });

});

$(window).load(function() {
    setTimeout(function() {
        $('.loader').css('display', 'none');
    }, 1000);
    $('.loader').addClass("animated slideOutUp");
});

function getPage() {
    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    }
    else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            $(".main").html(xmlhttp.responseText);
        }
    }

    xmlhttp.onload = function() {

        $('html, body').animate({
            scrollTop: 0
        }, 400);

        $(".table_panel").addClass("animated fadeInUp");
    }

    xmlhttp.open("POST", "/admin/" + page, true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xmlhttp.setRequestHeader(header,token);
    xmlhttp.send("");

    window.history.pushState(null, null, "/admin/" + page);

    var pathArray = window.location.pathname.split( '/' );
    if (pathArray[2]=="information") getGraphic();
}


function getGraphic() {
    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    }
    else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    var counts;

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            counts = xmlhttp.responseText;
            counts = JSON.parse(counts);
            drawGrafic0(counts);
            var ctx = document.getElementById("canvas").getContext("2d");
            window.myLine = new Chart(ctx, config0);
        }
    }

    xmlhttp.onload = function() {

    }

    xmlhttp.open("GET", "/admin/information/json", true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xmlhttp.send("");

}

var config0;

function drawGrafic0(counts) {


    var randomColor = function(opacity) {
        return 'rgba(255, 0, 0, 0.6)';
    };

    config0 = {
        type: 'line',
        data: {
            labels: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"],
            datasets: [{
                label: " ",
                data: counts,
                fill: false
            }]
        },
        options: {
            responsive: true,
            legend: {
                position: 'bottom',
            },
            hover: {
                mode: 'label'
            },
            animation: {
                duration: 2000
            },
            scales: {
                yAxes: [{
                    gridLines: {
                        lineWidth: 0,
                        color: "rgba(255,255,255,0)"
                    }
                }],
                xAxes: [{
                    gridLines: {
                        lineWidth: 0,
                        color: "rgba(255,255,255,0)"
                    }
                }]
            },
            title: {
                display: true,
                text: ''
            }
        }
    };

    Chart.defaults.global.legend.display = false;

    $.each(config0.data.datasets, function(i, dataset) {
        var background = randomColor();
        dataset.borderColor = background;
        dataset.backgroundColor = background;
        dataset.pointBorderColor = background;
        dataset.pointBackgroundColor = background;
        dataset.pointBorderWidth = 1;
    });

}


function setCurrentElemSidebar() {
    var pathArray = window.location.pathname.split( '/' );
    var rel = pathArray[2];

    if (pathArray[3] != null) {
        var childRel = pathArray[2] + "/" + pathArray[3];

        $('aside .menu .elem').each(function() {

            if ($(this).attr('rel') == rel) {

                $(this).children(".menu_icon").addClass("opened");
                $(this).siblings(".sub_menu").show(250);
            }

            if ($(this).attr('rel') == childRel) {
                $(this).addClass('current');
            }

        });
    }
    else {

        $('aside .menu .elem').each(function() {

            if ($(this).attr('rel') == rel) {
                $(this).addClass('current');

                $(this).children(".menu_icon").addClass("opened");
                $(this).siblings(".sub_menu").show(250);
            }

        });

    }


}

    