$(window).resize(changeUiShopPage);

function priceInputValidate(e) {
    let event = e || window.event;
    let charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

$(function () {
    changeUiShopPage();
})

function changeUiShopPage() {
    let searchBar = $('.search-form');
    let navbarWidth = $('.navbar').outerWidth();
    let totalWidth = $('.navbar-brand').width() + $('.navbar-nav').width();
    let totalWidthSmall = $('.navbar-brand').width() + $('.navbar-toggler').width();

    if ($('.navbar-expand-lg .navbar-toggler').css('display') == "none") {
        $('.search-form').css('width', `calc(100% - ${totalWidth}px - 50px)`);
    } else {
        $('.search-form').css('width', `calc(100% - ${totalWidthSmall}px - 50px)`);
    }

    if (window.matchMedia && window.matchMedia('(max-width: 767px)').matches) {
        $('.search-form').remove();
        $('header').append(searchBar);
        $('.search-form').css('width', `${navbarWidth}px`);
    } else {
        $('.search-form').remove();
        $(searchBar).insertAfter('.navbar-brand');
    }
    
}