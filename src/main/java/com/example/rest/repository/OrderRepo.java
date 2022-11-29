package com.example.rest.repository;

import com.example.rest.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Integer> {
    public List<OrderEntity> findAllByUser_IdOrderByTimestampDesc(Integer userId);
    default List<OrderEntity> getAllOrders(Integer userId) {
        return findAllByUser_IdOrderByTimestampDesc(userId);
    }


    @Query("SELECT SUM(m.price) FROM OrderEntity m where m.user.id = ?1")
    public Long selectTotalCost(Integer userId);
    @Query("SELECT SUM(m.price) FROM OrderEntity m")
    public Long selectTotalCostAllUsers();
}
