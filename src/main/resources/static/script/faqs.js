$('.accordion .card .btn-link').on('click', function() {
    if ($(this).parents('.card-header').siblings().css('display') == 'none') {
        $(this).find('i.fas').removeClass('fa-chevron-down');
        $(this).find('i.fas').addClass('fa-chevron-up');
    } else {
        $(this).find('i.fas').removeClass('fa-chevron-up');
        $(this).find('i.fas').addClass('fa-chevron-down');
    }
})