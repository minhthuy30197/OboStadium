$(function () {
  changeUi()
})

function changeUi() {
  $('.nav-sidebar .buying-order').addClass('active');
  $(".nav-link#account-setting").addClass('active-nav');
}

$('.order-status').click(function() {
    let status = $(this).data('status');
    $.ajax({
        url: '/api/get-order-list?status='+status,
        type: 'GET',
        contentType: "application/json; charset=utf-8",
        success: function(data) {
            $('.nav-link.active').toggleClass("active");
            $('.order-status[data-status='+status+']').toggleClass("active");
            showListOrder(data);
            formatMoney();
        },
        error: function(data) {
            toastr.warning(data.responseJSON.message);
        }
    });
})

function showListOrder(orders) {
    let html = '';
    for (order of orders) {
        html += `
                            <div class="item">
                                <div class="header-item">
                                    <div class="order-number-wrapper">
                                        Mã đơn <a class="order-number" href="/tai-khoan/lich-su-giao-dich/${order.id}">#${order.id}</a>
                                    </div>
                                    <a class="full-details" href="/tai-khoan/lich-su-giao-dich/${order.id}">Xem chi tiết</a>
                                </div>
                                <div class="item-info-wrapper">
                                    <div class="product-info-wrapper">
                                        <div class="thumbnails"><img class="img-fluid" src="${order.productImg}" alt="${order.productName}"/></div>
                                        <div class="product-info"><span class="name">${order.productName}</span>
                                            <div class="size"><span>${order.sizeUs}</span>US | <span>${order.sizeVn}</span>VN | <span>${order.sizeCm}</span>CM</div>
                                        </div>
                                    </div>
                                    <div class="price bid-price"><span class="text-price">${order.totalPrice}</span> &#x20AB;</div>
                                </div>
                            </div>
                            <div class="item-small">
                                <div class="order-number-wrapper">
                                    Mã đơn <a class="order-number" href="/tai-khoan/lich-su-giao-dich/${order.id}">#${order.id}</a>
                                </div>
                                <div class="item-info-wrapper">
                                    <div class="product-info-wrapper">
                                        <div class="product-info">
                                            <a class="name" href="/tai-khoan/lich-su-giao-dich/${order.id}">${order.productName}</a>
                                            <div class="size">
                                                <span>${order.sizeUs}</span>US | <span>${order.sizeVn}</span>VN | <span>${order.sizeCm}</span>CM
                                            </div>
                                        </div>
                                    </div>
                                    <div class="price bid-price"><span class="text-price">${order.totalPrice}</span> &#x20AB;</div>
                                </div>
                            </div>
        `
    }
    $('#list-order').html(html);
}

$('.cancel-btn').click(function() {
    let orderId = $(this).data("order");
    $.ajax({
            url: '/api/cancel-order/'+orderId,
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            success: function(data) {
                toastr.success("Hủy đơn hàng thành công");
                $('.status').html("Đã hủy");
                $('.cancel-btn').remove();
            },
            error: function(data) {
                toastr.warning(data.responseJSON.message);
            }
    });
})