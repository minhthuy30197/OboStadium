package com.company.demo.controller.anonymous;

import com.company.demo.entity.Brand;
import com.company.demo.entity.Category;
import com.company.demo.entity.Post;
import com.company.demo.model.dto.DetailProductInfoDto;
import com.company.demo.model.dto.ListProductDto;
import com.company.demo.model.dto.ProductInfoDto;
import com.company.demo.service.BlogService;
import com.company.demo.service.BrandService;
import com.company.demo.service.CategoryService;
import com.company.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import static com.company.demo.config.Constant.*;
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
    public String getShopPage(Model model, @RequestParam(required = false) Integer page) {
        // Get list brand
        List<Brand> brands = brandService.getListBrand();
        model.addAttribute("brands", brands);

        // Get list category
        List<Category> categories = categoryService.getListCategory();
        model.addAttribute("categories", categories);

        // Render list size
        model.addAttribute("sizeVn", SIZE_VN);

        // Get list product
        // Validate page number
        if (page == null) {
            page = 0;
        } else {
            page--;
            if (page < 0) {
                page = 0;
            }
        }
        ListProductDto result = productService.searchProduct(page);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentPage", result.getCurrentPage());
        model.addAttribute("listProduct", result.getProducts());

        return "shop/product";
    }

    @GetMapping("/san-pham/{slug}/{id}")
    public String getDetailProductPage(Model model, @PathVariable long id) {
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
