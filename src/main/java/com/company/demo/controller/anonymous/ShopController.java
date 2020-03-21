package com.company.demo.controller.anonymous;

import com.company.demo.entity.Brand;
import com.company.demo.entity.Category;
import com.company.demo.entity.Post;
import com.company.demo.model.dto.DetailProductInfoDto;
import com.company.demo.model.dto.ListProductDto;
import com.company.demo.model.dto.ProductInfoDto;
import com.company.demo.model.request.FilterProductReq;
import com.company.demo.service.BlogService;
import com.company.demo.service.BrandService;
import com.company.demo.service.CategoryService;
import com.company.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import static com.company.demo.config.Constant.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ShopController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String getIndexPage(Model model) {
        // Get new products
        List<ProductInfoDto> newProducts = productService.getListNewProduct();
        model.addAttribute("newProducts", newProducts);

        // Get best seller products
        List<ProductInfoDto> bestSellerProducts = productService.getListBestSellerProduct();
        model.addAttribute("bestSellerProducts", bestSellerProducts);

        // Get suggest products
        List<ProductInfoDto> suggestProducts = productService.getListSuggestProduct();
        model.addAttribute("suggestProducts", suggestProducts);

        // Get latest posts
        List<Post> latestPosts = blogService.getLatestPost();
        model.addAttribute("latestPosts", latestPosts);

        return "shop/index";
    }

    @GetMapping("/san-pham")
    public String getShopPage(Model model) {
        // Get list brand
        List<Brand> brands = brandService.getListBrand();
        model.addAttribute("brands", brands);
        ArrayList<Integer> brandIds = new ArrayList<Integer>();
        for (Brand brand : brands) {
            brandIds.add(brand.getId());
        }
        model.addAttribute("brandIds", brandIds);

        // Get list category
        List<Category> categories = categoryService.getListCategory();
        model.addAttribute("categories", categories);
        ArrayList<Integer> categoryIds = new ArrayList<Integer>();
        for (Category category : categories) {
            categoryIds.add(category.getId());
        }
        model.addAttribute("categoryIds", categoryIds);

        // Render list size
        model.addAttribute("sizeVn", SIZE_VN);

        // Get list product
        FilterProductReq req = new FilterProductReq(brandIds, categoryIds, new ArrayList<>(), (long) 0, Long.MAX_VALUE, 1);
        ListProductDto result = productService.searchProduct(req);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentPage", result.getCurrentPage());
        model.addAttribute("listProduct", result.getProducts());

        return "shop/product";
    }

    @PostMapping("/san-pham/loc")
    public ResponseEntity<?> filterProduct(@RequestBody FilterProductReq req) {
        // Validate
        if (req.getMinPrice() == null) {
            req.setMinPrice((long) 0);
        } else {
            if (req.getMinPrice() < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mức giá phải lớn hơn 0");
            }
        }
        if (req.getMaxPrice() == null) {
            req.setMaxPrice(Long.MAX_VALUE);
        } else {
            if (req.getMaxPrice() < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mức giá phải lớn hơn 0");
            }
        }

        ListProductDto result = productService.searchProduct(req);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/san-pham/{slug}/{id}")
    public String getDetailProductPage(Model model, @PathVariable String id) {
        // Get detail info
        DetailProductInfoDto product = productService.getDetailProductById(id);
        model.addAttribute("product", product);

        // Get related products
        List<ProductInfoDto> relatedProducts = productService.getRelatedProducts(id);
        model.addAttribute("relatedProducts", relatedProducts);

        // Get list available size
        List<Integer> availableSizes = productService.getListAvailableSize(id);
        model.addAttribute("availableSizes", availableSizes);
        if (availableSizes.size() > 0) {
            model.addAttribute("canBuy", true);
        } else {
            model.addAttribute("canBuy", false);
        }

        // Render list size
        model.addAttribute("sizeVn", SIZE_VN);
        model.addAttribute("sizeUs", SIZE_US);
        model.addAttribute("sizeCm", SIZE_CM);

        return "shop/detail";
    }
}
