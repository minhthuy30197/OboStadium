package com.company.demo.repository;

import com.company.demo.entity.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
    @Query(nativeQuery = true, value = "SELECT size FROM product_size WHERE product_id = ?1 AND quantity > 0")
    public List<Integer> findAllSizeOfProduct(String id);

    @Query(nativeQuery = true, value = "SELECT * FROM product_size WHERE product_id = ?1 AND size = ?2 AND quantity > 0")
    public ProductSize checkProductSizeAvailable(String productId, int size);
}
