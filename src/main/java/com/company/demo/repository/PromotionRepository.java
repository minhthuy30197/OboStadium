package com.company.demo.repository;

import com.company.demo.entity.Promotion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM promotion WHERE is_active = true AND is_public = true AND expired_at > now()")
    public Promotion checkHasPublicPromotion();

    @Query(nativeQuery = true, value = "SELECT * FROM promotion WHERE is_active = true AND coupon_code = ?1")
    public Promotion checkPromotion(String code);

    @Query(nativeQuery = true, value = "SELECT * FROM promotion " +
            "WHERE coupon_code LIKE CONCAT('%',:code,'%') AND name LIKE CONCAT('%',:name,'%') " +
            "AND is_public LIKE CONCAT('%',:ispublic,'%') AND is_active LIKE CONCAT('%',:active,'%')")
    public Page<Promotion> adminGetListPromotion(@Param("code") String code, @Param("name") String name, @Param("ispublic") String ispublic, @Param("active") String active, Pageable pageable);

    public Optional<Promotion> findByCouponCode(String code);

    @Query(nativeQuery = true, value = "SELECT count(id) FROM orders WHERE JSON_UNQUOTE(JSON_EXTRACT(promotion, '$.couponCode')) = ?1")
    public int checkPromotionInUse(String code);

    @Query(nativeQuery = true, value = "SELECT * FROM promotion WHERE expired_at > now() AND is_active = true")
    public List<Promotion> getAllValidPromotion();
}
