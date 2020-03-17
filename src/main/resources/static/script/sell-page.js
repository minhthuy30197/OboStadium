$(function () {
  changeUi();
  render();
  sizeGuideRender();
  activeSize();
  $('[data-toggle="tooltip"]').tooltip();
  sellNowRender();
})

function changeUi() {
  $('.size-details').text($('.size-info').text());
}

$('.price-input').on('change', function (e) {
  let subPrice = $(this).val();

  $('.sub-price-showing:not(.sell-now)').text(`${convertPrice(subPrice)} ₫`);

  // Processing asks price
  $('.process-price-showing:not(.sell-now)').text(`${convertPrice(subPrice * 0.05)} ₫`)

  // Count total price
  let totalPrice = currency($('.sub-price-showing:not(.sell-now)').text()).value - currency($('.process-price-showing:not(.sell-now)').text()).value + currency($('.shipping-price-showing:not(.sell-now)').text()).value;

  $('.total-price-showing:not(.sell-now)').text(`${convertPrice(totalPrice)} ₫`)

  // Validate minimum bid price
  $('.invalid-feedback').css('display', 'none')
  if (currency(convertPrice(subPrice)).value < currency('100,000')) {
    $('.invalid-feedback').text('Giá đặt bán phải lớn hơn 100,000 ₫');
    $('.sub-price-showing:not(.sell-now)').text("");
    $('.invalid-feedback').css('display', 'block');
    $('.total-price-showing:not(.sell-now)').text("");
  } else if (currency(convertPrice(subPrice)).value > currency($('.bid-price-info').text())) {
    $('button[href="#sell"]').tab('show');
    $('#asks').trigger('reset');
    $('.asks:not(.shipping-price-showing)').text("");

    $('.asks-btn').removeClass('red-btn');
    $('.asks-btn').addClass('trans-btn');

    $('.sell-btn').removeClass('trans-btn');
    $('.sell-btn').addClass('red-btn');
  }
})

function sellNowRender() {
  // Input render
  $('.price-input.sell-now').val($('.bid-price-info').text());

  // Subtotal render
  $('.sub-price-showing.sell-now').text($('.bid-price-info').text())

  // Process price render
  $('.process-price-showing.sell-now').text(`${convertPrice(Number($('.sub-price-showing.sell-now').text().slice(0, -2).replace(/,/g, "")) * 0.05)} ₫`);

  //Total Price render
  let totalPrice = currency($('.sub-price-showing:not(.asks)').text()).value - currency($('.process-price-showing.sell-now').text()).value + currency($('.shipping-price-showing.sell-now').text()).value;

  $('.total-price-showing.sell-now').text(`${new Intl.NumberFormat('vn-VN', {
    minimumFractionDigits: 0
  }).format(totalPrice)} ₫`)
}

$(document).on('click', function (e) {
  let target = e.target;

  if (target.closest('.size .item')) {
    $('.size-info').text($('.size-details').text());
  }

  if (target.closest('.asks-btn')) {
    $('.asks-btn').addClass('red-btn');
    $('.asks-btn').removeClass('trans-btn');

    $('.sell-btn').addClass('trans-btn');
    $('.sell-btn').removeClass('red-btn');

    $('#asks').trigger('reset');
    $('.asks:not(.shipping-price-showing)').text("");
  }
  else if (target.closest('.sell-btn')) {
    $('.asks-btn').removeClass('red-btn');
    $('.asks-btn').addClass('trans-btn');

    $('.sell-btn').addClass('red-btn');
    $('.sell-btn').removeClass('trans-btn');
  }
})