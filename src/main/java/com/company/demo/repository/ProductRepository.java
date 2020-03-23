package com.company.demo.repository;

import com.company.demo.entity.Product;
import com.company.demo.model.dto.ProductInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query(nativeQuery = true, name = "getListBestSellerProduct")
    public List<ProductInfoDto> getListBestSellerProduct(int limit);

    @Query(nativeQuery = true, name = "getListNewProduct")
    public List<ProductInfoDto> getListNewProduct(int limit);

    @Query(nativeQuery = true, name = "getListSuggestProduct")
    public List<ProductInfoDto> getListSuggestProduct(List<String> products, int limit);

    @Query(nativeQuery = true, name = "getRelatedProducts")
    public List<ProductInfoDto> getRelatedProducts(String id, int brandId, int limit);

    @Query(nativeQuery = true, name = "searchProductByKeyword")
    public List<ProductInfoDto> searchProductByKeyword(@Param("keyword") String keyword, @Param("limit") int limit, @Param("offset") int offset);

    @Query(nativeQuery = true, value = "SELECT count(DISTINCT product.id)\n" +
            "FROM product \n" +
            "INNER JOIN product_category \n" +
            "ON product.id = product_category.product_id \n" +
            "INNER JOIN category\n" +
            "ON category.id = product_category.category_id\n" +
            "WHERE product.is_available = true AND (product.name LIKE CONCAT('%',:keyword,'%') OR category.name LIKE CONCAT('%',:keyword,'%'))\n")
    public int countProductByKeyword(@Param("keyword") String keyword);

    @Query(nativeQuery = true, name = "searchProductBySize")
    public List<ProductInfoDto> searchProductBySize(List<Integer> brands, List<Integer> categories, long minPrice, long maxPrice, List<Integer> sizes, int limit, int offset);

    @Query(nativeQuery = true, value = "SELECT COUNT(DISTINCT d.id)\n" +
            "FROM (\n" +
            "SELECT DISTINCT product.id\n" +
            "FROM product \n" +
            "INNER JOIN product_category \n" +
            "ON product.id = product_category.product_id \n" +
            "WHERE product.is_available = true AND product.brand_id IN (?1) AND product_category.category_id IN (?2)\n" +
            "AND product.price > ?3 AND product.price < ?4) as d\n" +
            "INNER JOIN product_size \n" +
            "ON product_size.product_id = d.id\n" +
            "WHERE product_size.size IN (?5)")
    public int countProductBySize(List<Integer> brands, List<Integer> categories, long minPrice, long maxPrice, List<Integer> sizes);

    @Query(nativeQuery = true, name = "searchProductAllSize")
    public List<ProductInfoDto> searchProductAllSize(List<Integer> brands, List<Integer> categories, long minPrice, long maxPrice, int limit, int offset);

    @Query(nativeQuery = true, value = "SELECT COUNT(DISTINCT product.id)\n" +
            "FROM product \n" +
            "INNER JOIN product_category \n" +
            "ON product.id = product_category.product_id \n" +
            "WHERE product.is_available = true AND product.brand_id IN (?1) AND product_category.category_id IN (?2)\n" +
            "AND product.price > ?3 AND product.price < ?4\n")
    public int countProductAllSize(List<Integer> brands, List<Integer> categories, long minPrice, long maxPrice);
}
