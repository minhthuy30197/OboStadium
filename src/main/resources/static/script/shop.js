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

changeUi();

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

// Filter function
let brand = [];
let gender = [];
let size = [];
let price = [{}];
let dataSorted;
$(document).on('change', function(e) {
  let target = e.target;
  brand = $('.brand .select-filter .filter-checkbox:checked');
  gender = $('.category .select-filter .filter-checkbox:checked');
  price['from'] = $('#from-price').val();
  price['to'] = $('#to-price').val();
  console.log(brand)
  console.log(gender)
  console.log(price['from'])
  console.log(price['to'])

  if ($('.brand .select-filter .filter-checkbox').is(':checked') == false) {
    $(`.product-link`).show();
  }

  if (
    $('.filter-bar input').is(':checked') ||
    $('.size .item').hasClass('size-choose') ||
    $('.price-input').val() !== '' ||
    $('#filterModal .price-input').val() !== ''
  ) {
    $('.clear-filter')
      .not('.modal-footer .clear-filter')
      .removeAttr('disabled');
  } else {
    $('.clear-filter')
      .not('.modal-footer .clear-filter')
      .attr('disabled', 'disabled');
  }

  if (
    $('.price-input#from-price').val() == '' &&
    $('.price-input#to-price').val() == ''
  ) {
    $('.apply-price:not(.small)').attr('disabled', 'disabled');
  } else {
    $('.apply-price:not(.small)').removeAttr('disabled');
  }
});

$(document).on('click', function(e) {
  let target = e.target;

  if (target.closest('.filter-bar .size .item')) {
    $(e.target).toggleClass('size-choose');
    if ($('.size .item').hasClass('size-choose')) {
      $('.clear-filter')
        .not('.modal-footer .clear-filter')
        .removeAttr('disabled');
    } else {
      $('.clear-filter')
        .not('.modal-footer .clear-filter')
        .attr('disabled', 'disabled');
    }
  }

  if (target.closest('.clear-filter')) {
    $('.filter-bar input').prop('checked', false);
    $('.clear-filter')
      .not('.modal-footer .clear-filter')
      .attr('disabled', 'disabled');
    $('.product-link').show();
    brandArray = [];

    if ($('.filter-bar input').is(':checked')) {
      $('.filter-bar input').prop('checked', true);
    } else if ($('.size .item').hasClass('size-choose')) {
      $('.size .item').removeClass('size-choose');
    } else if (
      $('.price-input').val() !== '' ||
      $('.price-input.small').val() !== ''
    ) {
      $('.price-input').val('');
      $('.price-input.small').val('');
    }
  }
});