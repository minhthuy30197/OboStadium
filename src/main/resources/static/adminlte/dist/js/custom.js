$(function () {
  formatMoney();
})

function formatMoney() {
   $('.text-price').each(function (index, element) {
        money = $(element).text();
        $(element).text(convertPrice(money));
   });

   $('.text-price-input').each(function (index, element) {
        money = $(element).val();
        $(element).val(convertPrice(money));
   });
}

function convertPrice(currency) {
  let convert = new Intl.NumberFormat('vn-VN', {
    minimumFractionDigits: 0
  }).format(currency);

  return convert;
}