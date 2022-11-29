package com.example.rest.service;

import com.example.rest.DTO.CreateCurierDTO;
import com.example.rest.entity.CurierEntity;
import com.example.rest.repository.CurierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurierService {

    @Autowired
    private CurierRepo curierRepo;

    public List<CurierEntity> findAll() {
        return curierRepo.findAll();
    }

    public CurierEntity createCurierItem(CreateCurierDTO curierDTO) throws Exception {
        CurierEntity curierItem = new CurierEntity();
        curierItem.setName(curierDTO.getName());
        curierItem.setPhone(curierDTO.getPhone());
        curierItem.setOrders(new ArrayList<>());
        curierRepo.save(curierItem);
        return curierItem;
    }

    public CurierEntity editCurierInfo(Integer curierId, CurierEntity curierItem) throws Exception {
        CurierEntity curierItemFromDb = curierRepo.findById(curierId).orElseThrow(() -> new Exception("не найден"));
        curierItemFromDb.setName(curierItem.getName());
        curierItemFromDb.setPhone(curierItem.getPhone());
        curierItemFromDb.setStatus(curierItem.getStatus());
        curierRepo.save(curierItemFromDb);
        return curierItemFromDb;
    }

    public void delete(Integer curierId) throws Exception {
        CurierEntity curier = curierRepo.findById(curierId).orElseThrow(() -> new Exception("Не найдено"));
        if(curier.getStatus() != "free") {
            throw new Exception("Курьер несет заказ, его нельзя удалить");
        }
        curierRepo.deleteById(curierId);
    }
}
