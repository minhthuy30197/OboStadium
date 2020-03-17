$(function () {
    changeUiHomePage();
    carousel();
})


function changeUiHomePage() {
    $(".nav-link").removeClass('active-nav');

    if (window.matchMedia && window.matchMedia('(max-width: 768px)').matches) {
        $('#promo-banner-1').attr("src", "./image/banner/promo-banner-1-md.png");
        $('#promo-banner-2').attr("src", "./image/banner/promo-banner-2-md.png");
        $('#promo-banner-3').attr("src", "./image/banner/promo-banner-3-md.png");
        $('.navbar').css("padding-bottom", "0px");
        $('.search-form').css("padding-top", "0px");
        $('.search-form').css("padding-bottom", "20px");

    } else {
        $('#promo-banner-1').attr("src", "./image/banner/promo-banner-1-lg.png");
        $('#promo-banner-2').attr("src", "./image/banner/promo-banner-2-lg.png");
        $('#promo-banner-3').attr("src", "./image/banner/promo-banner-3-lg.png");
        $('.navbar').css("padding-bottom", "50px");
        $('.search-form').css("padding-top", "50px");
        $('.search-form').css("padding-bottom", "50px");
    }

    if (window.matchMedia && window.matchMedia('(max-width: 576px)').matches) {
        $('#promo-banner-1').attr("src", "./image/banner/promo-banner-1-sm.png");
        $('#promo-banner-2').attr("src", "./image/banner/promo-banner-2-sm.png");
        $('#promo-banner-3').attr("src", "./image/banner/promo-banner-3-sm.png");
    }
}

$(window).resize(changeUiHomePage);