package com.company.demo.repository;

import com.company.demo.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM promotion WHERE is_active = true AND expired_at > now()")
    public Promotion checkHasPromotion();
}
