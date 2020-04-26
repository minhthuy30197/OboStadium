$(function () {
  formatMoney();
  resetModal();
  configToastr();

  $(window).scroll(function () {
    if ($(this).scrollTop() > 100) {
      $('.back-to-top').fadeIn();
    } else {
      $('.back-to-top').fadeOut();
    }
  });
  $('.back-to-top').click(function () {
    $("html, body").animate({ scrollTop: 0 }, 600);
    return false;
  }); 
})

$(document).on('click', function (e) {
  let target = e.target;

  // Validate sign in
  if (target.closest('.sign-in-btn')) {
    $('.invalid-feedback').css('display', 'none');
    let emailFormat = new RegExp(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/);

    let isValid = true;
    let signInEmail = $('.sign-in-email');
    let signInEmailValue = signInEmail.val();
    let signInEmailInvalid = $('.sign-in-email').next();
    let signInPassword = $('.sign-in-password');
    let signInPasswordValue = signInPassword.val();
    let signInPasswordInvalid = $('.sign-in-password').next();


    if (signInEmailValue == "") {
      signInEmailInvalid.css('display', 'block');
      signInEmailInvalid.html('Vui lòng nhập email');
      isValid = false;
    } else if (!emailFormat.test(signInEmailValue)) {
      signInEmailInvalid.css('display', 'block');
      signInEmailInvalid.html('Email không hợp lệ');
      isValid = false;
    }
    if (signInPasswordValue.length < 4 || signInPasswordValue.length > 20) {
      signInPasswordInvalid.css('display', 'block');
      signInPasswordInvalid.html('Độ dài mật khẩu từ 4 - 20 ký tự')
      isValid = false;
    }

    if (isValid == true) {
        req = {
           email: signInEmailValue,
           password: signInPasswordValue
        }
        var myJSON = JSON.stringify(req);
        $.ajax({
           url: '/api/login',
           type: 'POST',
           data: myJSON,
           contentType: "application/json; charset=utf-8",
           success: function(data) {
               toastr.success("Đăng nhập thành công");
               signedValidate(true, data.fullName);
               $('.modal').modal('hide');
           },
           error: function(data) {
               toastr.warning(data.responseJSON.message);
           },
        });
    }
  }

  // Validate sign up
  if (target.closest('.sign-up-btn')) {
    let isValid = true;
    $('.invalid-feedback').css('display', 'none');
    let phoneFormat = new RegExp(/((09|03|07|08|05)+([0-9]{8})\b)/g);
    let emailFormat = new RegExp(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/);

    let fullName = $('.sign-up-full-name');
    let fullNameValue = fullName.val();
    let fullNameInvalid = fullName.next();
    let phone = $('.sign-up-phone');
    let phoneValue = phone.val();
    let phoneInvalid = phone.next();
    let email = $('.sign-up-email');
    let emailValue = email.val();
    let emailInvalid = email.next();
    let password = $('.sign-up-password');
    let passwordValue = password.val();
    let passwordInvalid = password.next();
    let confirmPassword = $('.sign-up-confirm-password');
    let confirmPasswordValue = confirmPassword.val();
    let confirmPasswordInvalid = confirmPassword.next();

    // Validate name
    if (fullNameValue == "") {
      fullNameInvalid.css('display', 'block');
      fullNameInvalid.html('Vui lòng nhập họ tên');
      isValid = false;
    }

    // Validate phone
    if (phoneValue == "") {
      phoneInvalid.css('display', 'block');
      phoneInvalid.html('Vui lòng nhập số điện thoại');
      isValid = false;
    } else if (!phoneFormat.test(phoneValue)) {
      phoneInvalid.css('display', 'block');
      phoneInvalid.html('Số điện thoại không hợp lệ');
      isValid = false;
    }

    // Validate email
    if (emailValue == "") {
      emailInvalid.css('display', 'block');
      emailInvalid.html('Vui lòng nhập email');
      isValid = false;
    } else if (!emailFormat.test(emailValue)) {
      emailInvalid.css('display', 'block');
      emailInvalid.html('Email không hợp lệ');
      isValid = false;
    }

    // Validate password
    if (passwordValue.length < 4 || passwordValue.length > 20) {
      passwordInvalid.css('display', 'block');
      passwordInvalid.html('Độ dài mật khẩu từ 4 - 20 ký tự');
      isValid = false;
    }

    // Validate confirm password
    if (confirmPasswordValue < 4 || passwordValue.length > 20) {
      confirmPasswordInvalid.css('display', 'block');
      confirmPasswordInvalid.html('Độ dài xác nhận mật khẩu từ 4 - 20 ký tự');
      isValid = false;
    } else if (confirmPasswordValue !== passwordValue) {
      confirmPasswordInvalid.css('display', 'block');
      confirmPasswordInvalid.html('Mật khẩu và xác nhận mật khẩu không giống nhau');
      isValid = false;
    }

    if (isValid == true) {
        req = {
            full_name: fullNameValue,
            email: emailValue,
            password: passwordValue,
            phone: phoneValue
        }
        var myJSON = JSON.stringify(req);
        $.ajax({
            url: '/api/register',
            type: 'POST',
            data: myJSON,
            contentType: "application/json; charset=utf-8",
            success: function(data) {
                toastr.success("Đăng ký thành công");
                signedValidate(true, data.fullName);
                $('.modal').modal('hide');
            },
            error: function(data) {
                toastr.warning(data.responseJSON.message);
            },
        });
    }
  }

  if (target.closest('.product-link')) {
    localStorage.setItem('sessionsProduct', $(this).attr('id'));

    if (target !== $('.product-link')) {
      let productId = $(target).parents('.product-link').attr('id');
      localStorage.setItem('sessionsProduct', productId);
    }
  }
})

function convertPrice(currency) {
  let convert = new Intl.NumberFormat('vn-VN', {
    minimumFractionDigits: 0
  }).format(currency);

  return convert;
}

function configToastr() {
    toastr.options = {
          "closeButton": true,
          "debug": false,
          "newestOnTop": false,
          "progressBar": false,
          "positionClass": "toast-top-center",
          "preventDuplicates": true,
          "onclick": null,
          "showDuration": "2000",
          "hideDuration": "1000",
          "timeOut": "1000",
          "extendedTimeOut": "1000",
          "showEasing": "swing",
          "hideEasing": "linear",
          "showMethod": "fadeIn",
          "hideMethod": "fadeOut"
    }
}

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

// Reset form after close modal

function resetModal() {
  $('.modal').on('hidden.bs.modal', function () {
    $(this).find('form').trigger('reset');
    $('.invalid-feedback').css('display', 'none');
  })
}

function signedValidate(status = false, fullname = '') {
  if (status == true) {
    isLogined = true;
    let signedLink = `
  <a id="account-setting" class="nav-link account-setting" href="/tai-khoan">Xin chào ${fullname}</a>`;

    $('.account-setting').replaceWith(signedLink);
  } else {
    isLogined = false;
    let notSignedLink = `
  <a class="nav-link account-setting" href="" data-toggle="modal" data-target="#signInSignUp">Tài khoản</a>
  `;
    $('.account-setting').replaceWith(notSignedLink);
  }
}

$(document).on('keyup', function (e) {
  let target = e.target;

  if (target.closest('.search-input')) {
        var keycode = (e.keyCode ? e.keyCode : e.which);
        if(keycode == '13'){
            searchProductByKeyword();
        }
  }
})


$('.search-button').click(function() {
    searchProductByKeyword();
})

function searchProductByKeyword() {
    let keyword = $('.search-input').val();
    if (keyword.length == 0) {
        toastr.warning("Vui lòng nhập từ khóa tìm kiếm");
        return
    }
    location.href="/api/tim-kiem?keyword="+keyword;
}

