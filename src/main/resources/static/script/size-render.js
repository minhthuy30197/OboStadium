function render() {
  let sizeVn = [38.5, 39, 40, 40.5, 41, 42, 42.5, 43, 44, 44.5, 45, 45.5, 46, 47, 47.5, 48, 48.5, 49.5, 50.5, 51.5];

  let sizeUs = [6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10, 10.5, 11, 11.5, 12, 12.5, 13, 13.5, 14, 15, 16, 17];

  let sizeCm = [23.7, 24.1, 24.5, 25, 25.4, 25.8, 26.2, 26.7, 27.1, 27.5, 27.9, 28.3, 28.8, 29.2, 29.6, 30, 30.5, 31.3, 32.2, 33];

  for (let i = 0; i < sizeVn.length; i++) {
    let sizeChoose = `
    <div class="item" data-size="${sizeUs[i]}US | ${sizeVn[i]}VN | ${sizeCm[i]}CM" data-id="${sizeVn[i]}">
        ${sizeVn[i]}
    </div>
    `;

    $('.size .select-filter').append(sizeChoose);
  };
};

function sizeGuideRender() {
  let sizeVn = [38.5, 39, 40, 40.5, 41, 42, 42.5, 43, 44, 44.5, 45, 45.5, 46, 47, 47.5, 48, 48.5, 49.5, 50.5, 51.5];

  let sizeUs = [6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10, 10.5, 11, 11.5, 12, 12.5, 13, 13.5, 14, 15, 16, 17];

  let sizeCm = [23.7, 24.1, 24.5, 25, 25.4, 25.8, 26.2, 26.7, 27.1, 27.5, 27.9, 28.3, 28.8, 29.2, 29.6, 30, 30.5, 31.3, 32.2, 33];
  let tr = '';
  for (let i = 0; i < sizeVn.length; i++) {
    tr += `
    <tr>
      <td>${sizeVn[i]}</td>
      <td>${sizeUs[i]}</td>
      <td>${sizeCm[i]}</td>
    </tr>
    `;
  }
  $('.side-guide-table tbody').html(tr);
}