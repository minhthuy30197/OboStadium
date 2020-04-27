$(function () {
  changeUi();
  render();
  sizeGuideRender();
  activeSize();
  buyNowRender();
});

function changeUi() {
  $('.size-details').text($('.size-info').text());
}

// Bid price input

$('.price-input').on('change', function (e) {
  // Convert input data
  let subPrice = $(this).val();
  let convertPrice = new Intl.NumberFormat('vn-VN', {
    minimumFractionDigits: 0
  }).format(subPrice);

  $('.sub-price-showing:not(.buy-now)').text(`${convertPrice} ₫`);

  // Count total price
  let totalPrice = currency($('.sub-price-showing:not(.buy-now)').text()).value + currency($('.shipping-price-showing:not(.buy-now)').text()).value;

  $('.total-price-showing:not(.buy-now)').text(`${new Intl.NumberFormat('vn-VN', {
    minimumFractionDigits: 0
  }).format(totalPrice)} ₫`)

  // Validate minimum bid price
  $('.invalid-feedback').css('display', 'none')
  if (currency(convertPrice).value < currency('100,000')) {
    $('.invalid-feedback').text('Giá đặt mua phải lớn hơn 100,000 ₫');
    $('.sub-price-showing:not(.buy-now)').text("");
    $('.invalid-feedback').css('display', 'block');
    $('.total-price-showing:not(.buy-now)').text("");
  } else if (currency(convertPrice).value > currency($('.ask-price-info').text())) {
    $('button[href="#profile"]').tab('show');
    $('#home').trigger('reset');
    $('.bid:not(.shipping-price-showing)').text("");
    $('.bid-btn').removeClass('green-btn');
    $('.bid-btn').addClass('trans-btn');

    $('.buy-btn').removeClass('trans-btn');
    $('.buy-btn').addClass('green-btn');
  }
})

// Buy now render data

function buyNowRender() {
  // Input render
  $('.price-input.buy-now').val($('.ask-price-info').text());

  // Subtotal render
  $('.sub-price-showing.buy-now').text($('.ask-price-info').text())

  //Total Price render
  let totalPrice = currency($('.sub-price-showing:not(.bid)').text()).value + currency($('.shipping-price-showing:not(.bid)').text()).value;

  $('.total-price-showing:not(.bid)').text(`${new Intl.NumberFormat('vn-VN', {
    minimumFractionDigits: 0
  }).format(totalPrice)} ₫`)
}

$(document).on('click', function (e) {
  let target = e.target;

  // Size details render
  if (target.closest('.size .item')) {
    $('.size-info').text($('.size-details').text());
  }

  // Buttons add class
  if (target.closest('.bid-btn')) {
    $('.bid-btn').addClass('green-btn');
    $('.bid-btn').removeClass('trans-btn');
    $('.buy-btn').addClass('trans-btn');
    $('.buy-btn').removeClass('green-btn');
    $('#home').trigger('reset');
    $('.bid:not(.shipping-price-showing)').text("");
  } else if (target.closest('.buy-btn')) {
    $('.bid-btn').removeClass('green-btn');
    $('.bid-btn').addClass('trans-btn');

    $('.buy-btn').addClass('green-btn');
    $('.buy-btn').removeClass('trans-btn');
  }
})

$('.address').on('keyup', function() {
  this.style.height = "0px";
  this.style.height = (16 + this.scrollHeight) + "px";
})