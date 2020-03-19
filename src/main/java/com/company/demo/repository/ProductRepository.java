package com.company.demo.repository;

import com.company.demo.entity.Product;
import com.company.demo.model.dto.ProductInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(nativeQuery = true, name = "getListBestSellerProduct")
    public List<ProductInfoDto> getListBestSellerProduct(int limit);

    @Query(nativeQuery = true, name = "getListNewProduct")
    public List<ProductInfoDto> getListNewProduct(int limit);

    @Query(nativeQuery = true, name = "getListSuggestProduct")
    public List<ProductInfoDto> getListSuggestProduct(List<Long> products, int limit);

    @Query(nativeQuery = true, name = "getRelatedProducts")
    public List<ProductInfoDto> getRelatedProducts(long id, List<Integer> categories, int brandId, int limit);

    @Query(nativeQuery = true, name = "searchProduct")
    public Page<ProductInfoDto> searchProduct(Pageable pageable);
}
