$(window).resize(changeUi);

$(function() {
  changeUi();
});

function changeUi() {
  let sizeItem = $('.filter-bar:not(.small-size) .size .item');
  let sizeChooseWidth = sizeItem.width();
  sizeItem.css('height', `${sizeChooseWidth}px`);

  $('.nav-link#shop').addClass('active-nav');

  if (
    $('.price-input#from-price').val() == '' &&
    $('.price-input#to-price').val() == ''
  ) {
    $('.apply-price:not(.small)').attr('disabled', 'disabled');
  } else {
    $('.apply-price:not(.small)').removeAttr('disabled');
  }
}

$('.filter-bar .title').on('click', function() {
  if ($(this).hasClass('collapsed') == false) {
    $(this)
      .children('i.fas')
      .addClass('fa-chevron-down');
    $(this)
      .children('i.fas')
      .removeClass('fa-chevron-up');
  } else {
    $(this)
      .children('i.fas')
      .addClass('fa-chevron-up');
    $(this)
      .children('i.fas')
      .removeClass('fa-chevron-down');
  }
});