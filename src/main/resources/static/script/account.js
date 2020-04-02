$(function() {
  changeUi()
})

function changeUi() {
  $('.nav-sidebar .account').addClass('active');
  $(".nav-link#account-setting").addClass('active-nav');
}

$('.btn-confirm-change-password').click(function() {
    let oldPassword = $('#old-password').val();
    let newPassword = $('#new-password').val();
    let retypePassword = $('#retype-password').val();
    let isValid = true;

    if (oldPassword.length < 4 || oldPassword.length > 20) {
        oldPasswordInvalid = $('#old-password').next();
        oldPasswordInvalid.css('display', 'block');
        oldPasswordInvalid.html('Độ dài mật khẩu cũ từ 4 - 20 ký tự');
        isValid = false;
    }

    if (newPassword.length < 4 || newPassword.length > 20) {
        newPasswordInvalid = $('#new-password').next();
        newPasswordInvalid.css('display', 'block');
        newPasswordInvalid.html('Độ dài mật khẩu mới từ 4 - 20 ký tự');
        isValid = false;
    }

    if (retypePassword.length < 4 || newPassword.length > 20) {
        retypePasswordInvalid = $('#retype-password').next();
        retypePasswordInvalid.css('display', 'block');
        retypePasswordInvalid.html('Độ dài xác thực mật khẩu từ 4 - 20 ký tự');
        isValid = false;
    } else if (newPassword !== retypePassword) {
        retypePasswordInvalid = $('#retype-password').next();
        retypePasswordInvalid.css('display', 'block');
        retypePasswordInvalid.html('Mật khẩu mới và xác nhận mật khẩu không giống nhau');
        isValid = false;
    }

    if (isValid) {
        req = {
            old_password: oldPassword,
            new_password: newPassword
        }
        var myJSON = JSON.stringify(req);
        $.ajax({
            url: '/api/change-password',
            type: 'POST',
            data: myJSON,
            contentType: "application/json; charset=utf-8",
            success: function(data) {
                toastr.success("Đổi mật khẩu thành công");
                clearInputChangePassword();
                $('.modal').modal('hide');
                formatMoney();
            },
            error: function(data) {
                toastr.warning(data.responseJSON.message);
            },
        });
    }
})

function clearInputChangePassword() {
    $('#old-password').val('');
    $('#old-password').next().hide();
    $('#new-password').val('');
    $('#new-password').next().hide();
    $('#retype-password').val('');
    $('#retype-password').next().hide();
}

$("#changePassword").on("hidden.bs.modal", function () {
    clearInputChangePassword();
});

$('.update-btn').click(function() {
    let phoneValue = $("#in-user-phone").val();
    let fullNameValue = $("#in-user-fullname").val();
    let addressValue = $('#in-user-address').val();
    let isValid = true;

    let phoneFormat = new RegExp(/((09|03|07|08|05)+([0-9]{8})\b)/g);
    if (phoneValue == "") {
        phoneInvalid = $("#in-user-phone").next();
        phoneInvalid.css('display', 'block');
        phoneInvalid.html('Vui lòng nhập số điện thoại');
        isValid = false;
    } else if (!phoneFormat.test(phoneValue)) {
        phoneInvalid = $("#in-user-phone").next();
        phoneInvalid.css('display', 'block');
        phoneInvalid.html('Số điện thoại không hợp lệ');
        isValid = false;
    }

    if (fullNameValue == "") {
        fullNameInvalid = $("#in-user-fullname").next();
        fullNameInvalid.css('display', 'block');
        fullNameInvalid.html('Vui lòng nhập họ tên');
        isValid = false;
    }

    if (isValid) {
        req = {
            phone: phoneValue,
            full_name: fullNameValue,
            address: addressValue
        }
        var myJSON = JSON.stringify(req);
        $.ajax({
            url: '/api/update-profile',
            type: 'POST',
            data: myJSON,
            contentType: "application/json; charset=utf-8",
            success: function(data) {
                signedValidate(true, fullNameValue);
                toastr.success("Cập nhật profile thành công");
            },
            error: function(data) {
                toastr.warning(data.responseJSON.message);
            },
            complete: function() {
                $("#in-user-fullname").next().hide();
                $("#in-user-phone").next().hide();
            }
        });
    }
})