package com.company.demo.repository;

import com.company.demo.entity.Order;
import com.company.demo.model.dto.OrderDetailDto;
import com.company.demo.model.dto.OrderInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(nativeQuery = true, name = "getListOrderOfPersonByStatus")
    public List<OrderInfoDto> getListOrderOfPersonByStatus(int status, long userId);

    @Query(nativeQuery = true, name = "userGetDetailById")
    public OrderDetailDto userGetDetailById(long id, long userId);

    public int countByProductId(String id);
}
