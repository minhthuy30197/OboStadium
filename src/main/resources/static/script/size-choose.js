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