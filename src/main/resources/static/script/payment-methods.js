$('.payment-methods-form').card({
  // a selector or DOM element for the container
  // where you want the card to appear
  container: '.card-wrapper',
  masks: {
    cardNumber: '•' // optional - mask card number
  },
  // all of the other options from above
});

$(document).on('click', function (e) {
  let target = e.target;
  if (target.closest('.payment-methods-save')) {
    if (
      $(".payment-method-card-number").val() !== "" && $('.payment-method-full-name').val() !== "" && $('.payment-method-expiry').val() !== "" &&
      $('.payment-method-cvc').val() !== "") {
      $('#paymentMethods').modal('hide');
      $('.visa-master-wrapper').addClass('added');
      let cardNumberShorten = `************${$(".payment-method-card-number").val().replace(/ /g, '').slice(-4)}`;
      let cardInfo = `
      <div class="radio-wrapper">
        <input class="cod-radio-btn" id="visa" type="radio" name="card-number" checked/>
        <label class="card" for="visa">
          <div class="radio-dot"></div>
          <img class="visa-master-img" src="./image/visa.png"/>
          <div class="card-number">${cardNumberShorten}</div>
        </label>
      </div>
      `;
      $('#visa-master').attr('checked', 'true');
      $('.card-place').append(cardInfo);  
      $('.add-new-payment-methods').css('display', 'block');
    }
  }
})