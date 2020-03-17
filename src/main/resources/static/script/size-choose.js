function activeSize() {
  $(`.item[data-size='${$('.size-details').text()}']`).addClass('size-choose');

  let getActiveProduct = productData.find(item => {
    return item['id'] == localStorage.getItem('sessionsProduct');
  })

  for (let i = 0; i < getActiveProduct['available_size'].length; i++) {
    $(`.item[data-id='${getActiveProduct['available_size'][i]}']`).addClass('cross');
  }
}

$(document).on('click', function (e) {
  let target = e.target;

  if (target.closest('.size .item')) {
    $('.size .item').removeClass('size-choose');
    $(target).addClass('size-choose');
    $('#sizeChooseModal').modal('hide');
    $('.size-details').text($(target).attr('data-size'));
  }
})

$('.size-guide').click(function () {
  $('body').addClass('modal1-on')
});

$('.go-back-size-choose').click(function () {
  $('body').addClass('modal2-on')
});

$('#sizeChooseModal').on('hidden.bs.modal', function () {
  $('body').addClass('modal1-on');

  if (!$('.modal.fade').hasClass('show')) {
    $('body').removeAttr('class');
  }
});

$('#sizeGuideModal').on('hidden.bs.modal', function () {
  $('body').addClass('modal2-on');

  if (!$('.modal.fade').hasClass('show')) {
    $('body').removeAttr('class');
  }
});