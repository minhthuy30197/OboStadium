package com.company.demo.repository;

import com.company.demo.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    @Query(nativeQuery = true, value = "SELECT link FROM image WHERE uploaded_by = ?1")
    public List<String> getListImageOfUser(long userId);

    public Image findByLink(String link);

    @Query(nativeQuery = true, value = "SELECT 1 FROM post, product WHERE (post.thumbnail = ?1) OR (JSON_CONTAINS(product.onfeet_images,CONCAT('\"',?1,'\"'),'$') > 0) OR (JSON_CONTAINS(product.product_images,CONCAT('\"',?1,'\"'),'$') > 0)")
    public Integer checkImgInUse(String link);
}
