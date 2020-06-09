package com.company.demo.service.impl;

import com.company.demo.entity.*;
import com.company.demo.exception.*;
import com.company.demo.model.dto.*;
import com.company.demo.model.mapper.*;
import com.company.demo.model.request.CreateProductReq;
import com.company.demo.model.request.FilterProductReq;
import com.company.demo.model.request.UpdateOnfeetImagesReq;
import com.company.demo.model.request.UpdateSizeCountReq;
import com.company.demo.repository.*;
import com.company.demo.service.*;
import com.company.demo.util.PageUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import static com.company.demo.config.Constant.*;
import java.sql.Timestamp;
import java.util.*;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private PromotionService promotionService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Override
    public List<ProductInfoDto> getListBestSellerProduct() {
        List<ProductInfoDto> products = productRepository.getListBestSellerProduct(5);

        return promotionService.checkPublicPromotion(products);
    }

    @Override
    public List<ProductInfoDto> getListNewProduct() {
        List<ProductInfoDto> products = productRepository.getListNewProduct(  5);

        return promotionService.checkPublicPromotion(products);
    }

    @Override
    public List<ProductInfoDto> getListSuggestProduct() {
        // Get Obo choices
        List<Configuration> configs = configurationRepository.findAll();
        if (configs.size() > 0) {
            Configuration config = configs.get(0);
            List<ProductInfoDto> products = productRepository.getListSuggestProduct(config.getOboChoices(), 5);

            return promotionService.checkPublicPromotion(products);
        }

        return null;
    }

    @Override
    public DetailProductInfoDto getDetailProductById(String id) {
        // Get product info
        Optional<Product> result = productRepository.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        Product product = result.get();

        if (!product.isAvailable()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        DetailProductInfoDto dto = ProductMapper.toDetailProductInfoDto(product);

        // Check promotion
        Promotion promotion = promotionService.checkPublicPromotion();
        if (promotion != null) {
            dto.setCouponCode(promotion.getCouponCode());
            dto.setPromotionPrice(promotionService.calculatePromotionPrice(dto.getPrice(), promotion));
        } else {
            dto.setCouponCode("");
        }

        return dto;
    }

    @Override
    public List<ProductInfoDto> getRelatedProducts(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        List<ProductInfoDto> products = productRepository.getRelatedProducts(id, 5);

        return promotionService.checkPublicPromotion(products);
    }

    @Override
    public List<Integer> getListAvailableSize(String id) {
        List<Integer> sizes = productSizeRepository.findAllSizeOfProduct(id);

        return sizes;
    }

    @Override
    public PageableDto filterProduct(FilterProductReq req) {
        int limit = 16;
        PageUtil page  = new PageUtil(limit, req.getPage());

        // Get list product and totalItems
        int totalItems;
        List<ProductInfoDto> products;
        if (req.getSizes().size() > 0) {
            products = productRepository.searchProductBySize(req.getBrands(), req.getCategories(), req.getMinPrice(), req.getMaxPrice(), req.getSizes(), limit, page.calculateOffset());

            totalItems = productRepository.countProductBySize(req.getBrands(), req.getCategories(), req.getMinPrice(), req.getMaxPrice(), req.getSizes());
        } else {
            products = productRepository.searchProductAllSize(req.getBrands(), req.getCategories(), req.getMinPrice(), req.getMaxPrice(), limit, page.calculateOffset());
            totalItems = productRepository.countProductAllSize(req.getBrands(), req.getCategories(), req.getMinPrice(), req.getMaxPrice());
        }

        // Calculate total pages
        int totalPages = page.calculateTotalPage(totalItems);

        PageableDto result = new PageableDto(promotionService.checkPublicPromotion(products), totalPages, req.getPage());

        return result;
    }

    @Override
    public PageableDto searchProductByKeyword(String keyword, Integer page) {
        // Validate
        if (keyword == null) {
            keyword = "";
        }
        if (page == null) {
            page = 1;
        }

        int limit = 15;
        PageUtil pageInfo = new PageUtil(limit, page);

        // Get list product and totalItems
        List<ProductInfoDto> products = productRepository.searchProductByKeyword(keyword, limit, pageInfo.calculateOffset());

        int totalItems = productRepository.countProductByKeyword(keyword);

        int totalPages = pageInfo.calculateTotalPage(totalItems);

        PageableDto result = new PageableDto(promotionService.checkPublicPromotion(products), totalPages, page);

        return result;
    }

    @Override
    public PageableDto adminGetListProduct(String id, String name, String category, String brand, String order, String direction, int page) {
        int limit = 15;
        PageUtil pageInfo  = new PageUtil(limit, page);

        List<Product> products = productRepository.adminGetListProduct(id, name, category, brand, order, direction, limit , pageInfo.calculateOffset());
        int totalItems = productRepository.countAdminGetListProduct(id, name, category, brand);

        int totalPages = pageInfo.calculateTotalPage(totalItems);

        return new PageableDto(products, totalPages, pageInfo.getPage());
    }

    @Override
    public String createProduct(CreateProductReq req) {
        // Validate info
        if (req.getCategoryIds().size() == 0) {
            throw new BadRequestException("Danh mục trỗng");
        }
        if (req.getProductImages().size() == 0) {
            throw new BadRequestException("Danh sách ảnh trống");
        }

        Product product = ProductMapper.toProduct(req);
        product.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        product.setTotalSold(0);
        // Gen id
        String productId = RandomStringUtils.randomAlphanumeric(6);
        product.setId(productId);

        try {
            productRepository.save(product);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi thêm sản phẩm");
        }

        return productId;
    }

    @Override
    public Product getProductById(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        return product.get();
    }

    @Override
    public void updateProduct(String id, CreateProductReq req) {
        // Check product exist
        Optional<Product> rs = productRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        // Validate info
        if (req.getCategoryIds().size() == 0) {
            throw new BadRequestException("Danh mục trỗng");
        }
        if (req.getProductImages().size() == 0) {
            throw new BadRequestException("Danh sách ảnh trống");
        }

        Product product = ProductMapper.toProduct(req);
        product.setId(id);

        try {
            productRepository.save(product);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi cập nhật thông tin sản phẩm");
        }
    }

    @Override
    public void updateOnfeetImages(String id, UpdateOnfeetImagesReq req) {
        // Check product exist
        Optional<Product> rs = productRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        Product product = rs.get();
        product.setOnfeetImages(req.getOnfeetImages());
        try {
            productRepository.save(product);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi cập nhật hình ảnh on feet");
        }
    }

    @Override
    public void updateSizeCount(UpdateSizeCountReq req) {
        // Check size is validate
        boolean isValid = false;
        for (int size : SIZE_VN) {
            if (size == req.getSize()) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new BadRequestException("Size không hợp lệ");
        }

        // Check product exist
        Optional<Product> rs = productRepository.findById(req.getProductId());
        if (rs.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        ProductSize ps = new ProductSize(req.getProductId(), req.getSize(), req.getCount());
        productSizeRepository.save(ps);
    }

    @Override
    public void deleteProduct(String id) {
        // Check product exist
        Optional<Product> rs = productRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        // If have order, can't delete
        int countOrder = orderRepository.countByProductId(id);
        if (countOrder > 0) {
            throw new BadRequestException("Sản phẩm đã được đặt hàng không thể xóa");
        }

        try {
            // Delete product size
            productSizeRepository.deleteByProductId(id);

            productRepository.deleteById(id);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new InternalServerException("Lỗi khi xóa sản phẩm");
        }
    }

    @Override
    public List<ProductSize> getListSizeOfProduct(String id) {
        return productSizeRepository.findByProductId(id);
    }

    @Override
    public List<ShortProductInfoDto> getAllProduct() {
        return productRepository.getAllProduct();
    }

    @Override
    public List<ShortProductInfoDto> getAvailableProducts() {
        return productRepository.getAvailableProducts();
    }

    @Override
    public boolean checkProductSizeAvailable(String productId, int size) {
        ProductSize ps = productSizeRepository.checkProductSizeAvailable(productId, size);
        if (ps != null) {
            return true;
        }

        return false;
    }
}
