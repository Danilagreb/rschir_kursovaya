package com.example.rest.service;


import com.example.rest.DTO.FilterGoodsDTO;
import com.example.rest.Helpers.Utilities;
import com.example.rest.entity.GoodsEntity;
import com.example.rest.repository.GoodsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GoodsService {

    @Autowired
    private GoodsRepo goodsRepo;

    public List<GoodsEntity> getAllGoods(FilterGoodsDTO dto) {
        int maxPrice = dto.getMaxPrice() == 0 ? Integer.MAX_VALUE : Math.min(dto.getMaxPrice(), Integer.MAX_VALUE);
        if(dto.getName().equals("")) {
            return goodsRepo.findAllByCurrentPriceBetween(Math.max(dto.getMinPrice(), 0), maxPrice);
        }
        return goodsRepo.filterByFields(dto.getName(), Math.max(dto.getMinPrice(), 0), maxPrice);
    }
    public List<GoodsEntity> getAllByIds(List<UUID> ids) {
        List<GoodsEntity> result = new ArrayList<>();
        for(UUID id: ids) {
            GoodsEntity item = goodsRepo.findById(id).orElseGet(null);
            if(item != null) {
                result.add(item);
            }
        }
        return result;
    }
    public GoodsEntity createGoodsItem(GoodsEntity goodsItem) throws Exception {
        goodsItem.setDiscount(0);
        if(goodsItem.getPrice() <= 0) {
            throw new Exception("Цена должна быть больше нуля");
        }
        goodsItem.setCurrentPrice(goodsItem.getPrice());
        goodsRepo.save(goodsItem);
        return goodsItem;
    }

    public GoodsEntity editDiscount(UUID goodsItemId, int discount) throws Exception {
        if(discount < 0) {
            discount = 0;
        } else if (discount > 99) {
            discount = 99;
        }
        GoodsEntity goodsItemFromDb = goodsRepo.findById(goodsItemId).orElseThrow(() -> new Exception("Товар не найден"));
        goodsItemFromDb.setDiscount(discount);
        goodsItemFromDb.setCurrentPrice(Utilities.ceil(goodsItemFromDb.getPrice() * (100 - discount) / 100));
        goodsRepo.save(goodsItemFromDb);
        return goodsItemFromDb;
    }

    public GoodsEntity editGoodsInfo(UUID goodsItemId, GoodsEntity goodsItem) throws Exception {
        GoodsEntity goodsItemFromDb = goodsRepo.findById(goodsItemId).orElseThrow(() -> new Exception("Товар не найден"));
        if(goodsItem.getPrice() <= 0) {
            throw new Exception("Цена должна быть больше нуля");
        }
        goodsItemFromDb.setName(goodsItem.getName());
        goodsItemFromDb.setDescription(goodsItem.getDescription());
        goodsItemFromDb.setGoodsType(goodsItem.getGoodsType());
        goodsItemFromDb.setPrice(goodsItem.getPrice());
        goodsItemFromDb.setCurrentPrice(Utilities.ceil(goodsItemFromDb.getPrice() * (100 - goodsItemFromDb.getDiscount()) / 100));
        goodsItemFromDb.setImg(goodsItem.getImg());
        goodsRepo.save(goodsItemFromDb);
        return goodsItemFromDb;
    }
}
