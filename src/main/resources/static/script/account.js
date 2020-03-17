$(function() {
  accountDetailsRender();
  changeUi()
})

function changeUi() {
  $('.nav-sidebar .account').addClass('active');
  $(".nav-link#account-setting").addClass('active-nav');
}

$('.sign-out-btn').on('click', function() {
  signed = false;
  DB.setSignedStatus(signed);
  localStorage.removeItem('signed-account');
});

function accountDetailsRender() {
  if (!$.isEmptyObject(DB.getSignedAccount())) {
    let getDetails = DB.getSignedAccount()['current-account-details']['account-details'];

    $('.full-name .form-control').val(getDetails['full-name']);

    $('.email .form-control').val(getDetails['email']);

    $('.password .form-control').val(getDetails['password']);

    $('.phone .form-control').val(getDetails['phone']);
  }
}