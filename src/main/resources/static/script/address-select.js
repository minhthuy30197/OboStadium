$(function () {
  addressSelectRender();

  if (!$.isEmptyObject(CURRENT_SIGNED_ACCOUNT)) {
    if (CURRENT_SIGNED_ACCOUNT["current-account-details"]['shipping-info-count'] !== undefined) {
      shippingInfoCount = CURRENT_SIGNED_ACCOUNT["current-account-details"]['shipping-info-count'];
    };

    if (!$.isEmptyObject(CURRENT_SIGNED_ACCOUNT['current-account-details']['default-address']) || CURRENT_SIGNED_ACCOUNT['current-account-details']['default-address'] !== undefined) {

      $('.info-wrapper').prepend(renderDefaultAddress());
    };
  }

  changeUi();

  if ($('.info-wrapper').html() == "" && !$.isEmptyObject(CURRENT_SIGNED_ACCOUNT) ) {
    $('#addNewShipping').modal('show');
    let text = `
    <div class="modal-sub-title">Để đặt hàng, vui lòng thêm địa chỉ nhận hàng</div>
    `
    $('#addNewShipping .modal-title').append(text)
  }
});

// UI

function addressSelectRender() {
  let city = [
    "Hà Nội",
    "TP Hồ Chí Minh",
    "An Giang",
    "Bà Rịa - Vũng Tàu",
    "Bắc Giang",
    "Bắc Kạn",
    "Bạc Liêu",
    "Bắc Ninh",
    "Bến Tre",
    "Bình Định",
    "Bình Dương",
    "Bình Phước",
    "Bình Thuận",
    "Cà Mau",
    "Cao Bằng",
    "Đắk Lắk",
    "Đắk Nông",
    "Điện Biên",
    "Đồng Nai",
    "Đồng Tháp",
    "Gia Lai",
    "Hà Giang",
    "Hà Nam",
    "Hà Tĩnh",
    "Hải Dương",
    "Hậu Giang",
    "Hòa Bình",
    "Hưng Yên",
    "Khánh Hòa",
    "Kiên Giang",
    "Kon Tum",
    "Lai Châu",
    "Lâm Đồng",
    "Lạng Sơn",
    "Lào Cai",
    "Long An",
    "Nam Định",
    "Nghệ An",
    "Ninh Bình",
    "Ninh Thuận",
    "Phú Thọ",
    "Quảng Bình",
    "Quảng Nam",
    "Quảng Ngãi",
    "Quảng Ninh",
    "Quảng Trị",
    "Sóc Trăng",
    "Sơn La",
    "Tây Ninh",
    "Thái Bình",
    "Thái Nguyên",
    "Thanh Hóa",
    "Thừa Thiên Huế",
    "Tiền Giang",
    "Trà Vinh",
    "Tuyên Quang",
    "Vĩnh Long",
    "Vĩnh Phúc",
    "Yên Bái",
    "Phú Yên",
    "Cần Thơ",
    "Đà Nẵng",
    "Hải Phòng"
  ]
  let option = `<option>Tỉnh/Thành phố</option>`;
  for (let i = 0; i < city.length; i++) {
    option += `
    <option value="${city[i]}">${city[i]}</option>
    `
  };
  $('.city').html(option);
}

$(document).on('change', function (e) {
  let target = e.target;

  if (target.closest('.city')) {
    let district = [
      "Quận Đống Đa",
      "Huyện Ba Vì",
      "Huyện Chương Mỹ",
      "Huyện Đan Phượng",
      "Huyện Đông Anh",
      "Huyện Gia Lâm",
      "Huyện Hoài Đức",
      "Huyện Mê Linh",
      "Huyện Mỹ Đức",
      "Huyện Phú Xuyên",
      "Huyện Phúc Thọ",
      "Huyện Quốc Oai",
      "Huyện Sóc Sơn",
      "Huyện Thạch Thất",
      "Huyện Thanh Oai",
      "Huyện Thanh Trì",
      "Huyện Thường Tín",
      "Huyện Ứng Hòa",
      "Quận Ba Đình",
      "Quận Bắc Từ Liêm",
      "Quận Cầu Giấy",
      "Quận Hà Đông",
      "Quận Hai Bà Trưng",
      "Quận Hoàn Kiếm",
      "Quận Hoàng Mai",
      "Quận Long Biên",
      "Quận Nam Từ Liêm",
      "Quận Tây Hồ",
      "Quận Thanh Xuân",
      "Thị Xã Sơn Tây"
    ]
    let option = `<option>Quận/Huyện</option>`;

    if ($('.city').val() == "Hà Nội") {
      for (let i = 0; i < district.length; i++) {
        option += `
    <option value="${district[i]}">${district[i]}</option>
    `
      };
      $('.district').html(option);
    } else {
      $('.district').html("")
    }
  }

  if (target.closest('.district')) {
    let ward = [
      "Phường Cát Linh",
      "Phường Hàng Bột",
      "Phường Khâm Thiên",
      "Phường Khương Thượng",
      "Phường Kim Liên",
      "Phường Láng Hạ",
      "Phường Láng Thượng",
      "Phường Nam Đồng",
      "Phường Ngã Tư Sở",
      "Phường Ô Chợ Dừa",
      "Phường Phương Liên",
      "Phường Phương Mai",
      "Phường Quang Trung",
      "Phường Quốc Tử Giám",
      "Phường Thịnh Quang",
      "Phường Thổ Quan",
      "Phường Trung Liệt",
      "Phường Trung Phụng",
      "Phường Trung Tự",
      "Phường Văn Chương",
      "Phường Văn Miếu"
    ]
    let option = `<option>Phường/Xã</option>`;

    if ($('.district').val() == "Quận Đống Đa") {
      for (let i = 0; i < ward.length; i++) {
        option += `
    <option value="${ward[i]}">${ward[i]}</option>
    `
      };
      $('.ward').html(option);
    } else {
      $('.ward').html("")
    }
  }
})

// Validate Address

$('.save').on('click', function () {
  let isValid = true;

  let fullName = $('.full-name');
  let fullNameValue = fullName.val();
  let fullNameInvalid = fullName.next();

  let phone = $('.phone');
  let phoneValue = phone.val();
  let phoneInvalid = phone.next();

  let city = $('.city');
  let cityValue = city.val();
  let cityInvalid = city.next();

  let district = $('.district');
  let districtValue = district.val();
  let districtInvalid = district.next();

  let ward = $('.ward');
  let wardValue = ward.val();
  let wardInvalid = ward.next();

  let address = $('#addNewShipping .address');
  let addressValue = address.val();
  let addressInvalid = address.next();

  if (SIGNUP_DATA["accounts"][CURRENT_ACCOUNT_DETAILS][0]['addresses'] == undefined) {
    SIGNUP_DATA["accounts"][CURRENT_ACCOUNT_DETAILS][0]['addresses'] = [];
    CURRENT_SIGNED_ACCOUNT['current-account-details']['addresses'] = [];
  }

  $('.invalid-feedback').css('display', 'none');

  // Validate fullName

  if (fullNameValue == "") {
    fullNameInvalid.html(`${$(fullName).attr('data-name')} không được để trống`);
    fullNameInvalid.css('display', 'block');
    isValid = false;
  }

  // Validate phone

  if (phoneValue == "") {
    phoneInvalid.html(`${$(phone).attr('data-name')} không được để trống`);
    phoneInvalid.css('display', 'block');
    isValid = false;
  }

  // Validate city

  if (cityValue == "Tỉnh/Thành phố") {
    cityInvalid.html(`Vui lòng chọn ${$(city).attr('data-name')}`);
    cityInvalid.css('display', 'block');
    isValid = false;
  }

  // Validate district

  if (districtValue == "Quận/Huyện") {
    districtInvalid.html(`Vui lòng chọn ${$(district).attr('data-name')}`);
    districtInvalid.css('display', 'block');
    isValid = false;
  }

  // Validate ward

  if (wardValue == "Phường/Xã") {
    wardInvalid.html(`Vui lòng chọn ${$(ward).attr('data-name')}`);
    wardInvalid.css('display', 'block');
    isValid = false;
  }

  // Validate address

  if (addressValue == "") {
    addressInvalid.html(`Vui lòng nhập ${$(address).attr('data-name')}`);
    addressInvalid.css('display', 'block');
    isValid = false;
  }

  // Config Data
  if (isValid == true) {
    shippingInfoCount++;
    $('.btns').css('display', 'block');
    // Set shipping data

    NEW_SHIPPING_DATA.push({ 'name': fullNameValue, 'phone': phoneValue, 'address': addressValue, 'district': districtValue, 'ward': wardValue, 'city': cityValue, 'id': `shipping-info-${shippingInfoCount}` });

    SIGNUP_DATA["accounts"][CURRENT_ACCOUNT_DETAILS][0]['addresses'].push(NEW_SHIPPING_DATA[0]);
    CURRENT_SIGNED_ACCOUNT['current-account-details']['addresses'].push(NEW_SHIPPING_DATA[0]);

    DB.setAccountData(SIGNUP_DATA);
    DB.setSignedAccount(CURRENT_SIGNED_ACCOUNT);

    $('.modal').modal('hide');

    let getNewShippingData = NEW_SHIPPING_DATA[0];

    // Get shipping data
    let newShippingElement = `
    <div class="info-choose radio-wrapper" id="shipping-info-${shippingInfoCount}">
      <input class="address-radio-btn" id="${getNewShippingData['name']}-address" type="radio" name="address-info" checked="" />
      <label for="${getNewShippingData['name']}-address">
        <div class="radio-dot"></div>
        <div class="shipping-data">
          <div class="name-phone">
            <span class="shipping-name">
              <b>${getNewShippingData['name']}</b>
            </span>
            |
            <span class="shipping-phone">${getNewShippingData['phone']}</span>
          </div>
          <div class="address">
            <span class="shipping-adress">${getNewShippingData['address']}, </span>
            <span class="shipping-ward">${getNewShippingData['ward']}, </span>
            <span class="shipping-district">${getNewShippingData['district']}, </span>
            <span class="shipping-city">${getNewShippingData['city']}</span>
          </div>
        </div>
      </label>
    </div>
    `;

    $('.info-wrapper').append(newShippingElement);

    SIGNUP_DATA["accounts"][CURRENT_ACCOUNT_DETAILS][0]['shipping-info-count'] = shippingInfoCount;

    CURRENT_SIGNED_ACCOUNT["current-account-details"]['shipping-info-count'] = shippingInfoCount;

    DB.setAccountData(SIGNUP_DATA);
    DB.setSignedAccount(CURRENT_SIGNED_ACCOUNT);
    NEW_SHIPPING_DATA = [];
  }
});

$(document).on('click', function (e) {
  let target = e.target;

  let checkedShippingInfoId = $('input[name=address-info]:checked').parent()
  let selectedShippingInfo = $('input[name=address-info]:checked').parent();

  if (target.closest('.add-new')) {
    if (CURRENT_ACCOUNT_DETAILS == undefined) {
      $('#signInSignUp').modal('show');
    } else {
      $(target).attr('data-toggle', 'modal');
      $(target).attr('data-target', '#addNewShipping');
    }
  }

  if (target.closest('.complete')) {
    let changeBtn = `
    <button class="btn btn-primary change red-btn">Thay đổi</button>
    `;
    $('.btns').css('display', 'none');
    $('.add-new').replaceWith(changeBtn);

    $('.info-choose').remove();
    $('.info-wrapper').append(selectedShippingInfo);

    let checkedShippingInfoIndex;
    if (CURRENT_SIGNED_ACCOUNT["current-account-details"]['addresses'].length !== 0) {
      NEW_SHIPPING_DATA = DB.getSignedAccount()["current-account-details"]['addresses'];

      checkedShippingInfoIndex = NEW_SHIPPING_DATA.findIndex(shippingData =>
        shippingData['id'] == checkedShippingInfoId.attr('id')
      );
    };

    if (checkedShippingInfoIndex !== -1) {
      let checkedShippingInfo = NEW_SHIPPING_DATA.splice(checkedShippingInfoIndex, 1);

      SIGNUP_DATA["accounts"][CURRENT_ACCOUNT_DETAILS][0]['default-address'] = checkedShippingInfo[0];

      CURRENT_SIGNED_ACCOUNT['current-account-details']['default-address'] = checkedShippingInfo[0];

      DB.setAccountData(SIGNUP_DATA);
      DB.setSignedAccount(CURRENT_SIGNED_ACCOUNT);
    }
  }

  if (target.closest('.change')) {
    let addNewBtn = `
    <button class="btn btn-primary red-btn add-new" data-toggle="modal" data-target="#addNewShipping">+ Thêm Mới</button>
    `;
    $(target).replaceWith(addNewBtn);
    $('.btns').css('display', 'block');

    $('.info-choose').remove();
    let getData = CURRENT_SIGNED_ACCOUNT["current-account-details"]["addresses"];
    let unCheckedShippingElement = "";
    let checkedShippingElement = "";
    for (let i = 0; i < getData.length; i++) {
      if (getData[i]['id'] == checkedShippingInfoId.attr('id')) {
        checkedShippingElement += `
    <div class="info-choose radio-wrapper" id="${getData[i]['id']}">
      <input class="address-radio-btn" id="${getData[i]['name']}-address" type="radio" name="address-info" checked />
      <label for="${getData[i]['name']}-address">
        <div class="radio-dot"></div>
        <div class="shipping-data">
          <div class="name-phone">
            <span class="shipping-name">
              <b>${getData[i]['name']}</b>
            </span>
            |
            <span class="shipping-phone">${getData[i]['phone']}</span>
          </div>
          <div class="address">
            <span class="shipping-adress">${getData[i]['address']}, </span>
            <span class="shipping-ward">${getData[i]['ward']}, </span>
            <span class="shipping-district">${getData[i]['district']}, </span>
            <span class="shipping-city">${getData[i]['city']}</span>
          </div>
        </div>
      </label>
    </div>
    `;
      } else {
        unCheckedShippingElement += `
    <div class="info-choose radio-wrapper" id="${getData[i]['id']}">
      <input class="address-radio-btn" id="${getData[i]['name']}-address" type="radio" name="address-info" />
      <label for="${getData[i]['name']}-address">
        <div class="radio-dot"></div>
        <div class="shipping-data">
          <div class="name-phone">
            <span class="shipping-name">
              <b>${getData[i]['name']}</b>
            </span>
            |
            <span class="shipping-phone">${getData[i]['phone']}</span>
          </div>
          <div class="address">
            <span class="shipping-adress">${getData[i]['address']}, </span>
            <span class="shipping-ward">${getData[i]['ward']}, </span>
            <span class="shipping-district">${getData[i]['district']}, </span>
            <span class="shipping-city">${getData[i]['city']}</span>
          </div>
        </div>
      </label>
    </div>
    `;
      }
    }
    $('.info-wrapper').prepend(checkedShippingElement);
    $('.info-wrapper').append(unCheckedShippingElement);
  }

  if (target.closest('.cancel')) {
    let changeBtn = `
    <button class="btn btn-primary change red-btn">Thay đổi</button>
    `;
    
    $('.btns').css('display', 'none');
    $('.add-new').replaceWith(changeBtn);
    $('.info-choose').remove();
    $('.info-wrapper').append(renderDefaultAddress());
  }
})

let NEW_SHIPPING_DATA = [];
let shippingInfoCount = 0;

function changeUi() {
  if ($('.info-wrapper').html() == "") {
    let addNewBtn = `
    <button class="btn btn-primary red-btn add-new">+ Thêm Mới</button>
    `;
    $(('.change')).replaceWith(addNewBtn);
  } else {
    let changeBtn = `
    <button class="btn btn-primary change red-btn">Thay đổi</button>
    `;
    $('.add-new').replaceWith(changeBtn);
  }
}

function renderDefaultAddress() {
  let defaultShippingData = DB.getSignedAccount()['current-account-details']['default-address'];

  let defaultShippingInfo = `
    <div class="info-choose radio-wrapper" id="${defaultShippingData['id']}">
      <input class="address-radio-btn" id="${defaultShippingData['name']}-address" type="radio" name="address-info" checked="" />
      <label for="${defaultShippingData['name']}-address">
        <div class="radio-dot"></div>
        <div class="shipping-data">
          <div class="name-phone">
            <span class="shipping-name">
              <b>${defaultShippingData['name']}</b>
            </span>
            |
            <span class="shipping-phone">${defaultShippingData['phone']}</span>
          </div>
          <div class="address">
            <span class="shipping-adress">${defaultShippingData['address']}, </span>
            <span class="shipping-ward">${defaultShippingData['ward']}, </span>
            <span class="shipping-district">${defaultShippingData['district']}, </span>
            <span class="shipping-city">${defaultShippingData['city']}</span>
          </div>
        </div>
      </label>
    </div>
    `;
  return defaultShippingInfo;
}