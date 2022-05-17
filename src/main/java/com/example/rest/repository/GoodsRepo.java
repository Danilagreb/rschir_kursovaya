package com.example.rest.repository;

import com.example.rest.entity.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GoodsRepo extends JpaRepository<GoodsEntity, UUID> {
    List<GoodsEntity> findAllByNameAndCurrentPriceBetweenOrDescriptionAndCurrentPriceBetweenOrGoodsTypeAndCurrentPriceBetween(String name,int min1, int max1, String description, int min2, int max2, String goodsType, int min3, int max3);

    default List<GoodsEntity> filterByFields(String name, int minPrice, int maxPrice) {
        return findAllByNameAndCurrentPriceBetweenOrDescriptionAndCurrentPriceBetweenOrGoodsTypeAndCurrentPriceBetween(name, minPrice, maxPrice, name, minPrice, maxPrice, name, minPrice, maxPrice);
    }

    List<GoodsEntity> findAllByCurrentPriceBetween(int minPrice, int maxPrice);

}
