$(function () {
  formatMoney();

  //Initialize Select2 Elements
  $('.select2bs4').select2({
     theme: 'bootstrap4'
  })
})

function formatMoney() {
   $('.text-price').each(function (index, element) {
        money = $(element).text();
        $(element).text(convertPrice(money));
   });
}

$('.text-price-input').keyup(function (event) {
    if (event.which >= 37 && event.which <= 40) return;
    $(this).val(function (index, value) {
        return value
          .replace(/\D/g, "")
    });
})


function convertPrice(currency) {
  let convert = new Intl.NumberFormat('vn-VN', {
    minimumFractionDigits: 0
  }).format(currency);

  return convert;
}