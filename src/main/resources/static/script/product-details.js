$(function () {
    caroselProductImage();
    imageZoom();
    instafeed();
    render();
    activeSize();
    sizeGuideRender();
    changeUi();
    relatedProductRender()
});

function caroselProductImage() {
    $('.slider-for').slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: false,
        fade: true,
        asNavFor: '.slider-nav',
    });

    $('.slider-nav').slick({
        slidesToShow: 3, // 3,
        slidesToScroll: 1,
        asNavFor: '.slider-for',
        dots: false,
        centerMode: true,
        focusOnSelect: true,
        arrows: false,
        responsive: [
            {
                breakpoint: 1024,
                settings: {
                    slidesToShow: 3,
                    slidesToScroll: 1,
                }
            },
            {
                breakpoint: 600,
                settings: {
                    slidesToShow: 3,
                    slidesToScroll: 1
                }
            },
            {
                breakpoint: 480,
                settings: {
                    slidesToShow: 3,
                    slidesToScroll: 1
                }
            }
        ]
    });
};

function imageZoom() {
    $('.img-zoom-container').zoom({
        magnify: 1.05
    });
}

function instafeed() {
    var userFeed = new Instafeed({
        get: 'user',
        userId: '330880789',
        limit: 12,
        resolution: 'standard_resolution',
        accessToken: '330880789.8a4b3ba.6801db3f819744bcb3a10ecf47bc4a5b',
        sortBy: 'most-recent',
        filter: function (image) {
            return image.tags.indexOf('adidasyeezyboost700waverunner') >= 0;
        },
        template: '<div class="col-lg-3 instaimg"><a class="instafeed-wrap" href="{{link}}" target="_blank"><div class="instaimg-wrap"><img src="{{image}}" alt="{{caption}}" class="img-fluid insta-img"/><div class="instaimg-overlay"><div class="like-and-comment"><label><i class="fas fa-heart"></i> {{model.likes.count}}</label> <label><i class="fas fa-comment"></i></i> {{model.comments.count}}</label></div></div></div></a></div>',
        after: function () {
            caroselInstafeed();
            $('#instafeed').addClass('grid-instafeed')
        }
    });

    userFeed.run();
    $('.slick-track').append($('.instaimg'));
}

function caroselInstafeed() {
    $('#instafeed').slick({
        dots: false,
        infinite: true,
        slidesToShow: 3,
        slidesToScroll: 3,
        arrows: true,
        appendArrows: $('.instagram-image .slide-btn'),
        accessibility: false,
        centerMode: true,
        dots: true,
        responsive: [
            {
                breakpoint: 1024,
                settings: {
                    slidesToShow: 3,
                    slidesToScroll: 3,
                }
            },
            {
                breakpoint: 800,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1,
                }
            },
            {
                breakpoint: 480,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1,
                    arrows: false,
                }
            }
        ]
    });
}

function changeUi() {
    $(".nav-link#shop").addClass('active-nav');
}

function relatedProductRender() {
    let relatedProductData = productData.filter(item => {
        return item['name'].toLowerCase().includes('yeezy')
    });
    let relatedProduct = "";

    for (let i = 0; i < 5; i++) {
        // Best Seller Elements
        relatedProduct += `
            <a class="product product-link position-relative" id="${relatedProductData[i]['id']}" href="./product-details.html">
                <div class="card">
                    <img src="${relatedProductData[i]['thumbnail']}" class="card-img-top"
                        alt="${relatedProductData[i]['name']}">
                    <div class="card-body">
                        <h5 class="card-title">${relatedProductData[i]['name']}</h5>
                        <p class="card-text price-desc">Giá thấp nhất hiện tại</p>
                        <p class="price">${currency(relatedProductData[i]['sell_price'], { separator: ',', precision: 0 }).format()} ₫</p>
                        <p class="card-text sold">Đã bán ${relatedProductData[i]['total_sold']} đôi</p>
                    </div>
                </div>
                <div class="shadow mx-auto position-absolute"></div>
            </a>
        `;
    }

    $('.products-showcase .product-row').html(relatedProduct);
}