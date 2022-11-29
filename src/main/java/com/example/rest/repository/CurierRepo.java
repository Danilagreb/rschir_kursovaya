package com.example.rest.repository;

import com.example.rest.entity.CurierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CurierRepo extends JpaRepository<CurierEntity, Integer> {
    public Optional<CurierEntity> findByNameIgnoreCase(String name);
    public List<CurierEntity> findAllByStatusEquals(String status);
    default List<CurierEntity> findAllFree() {
        return findAllByStatusEquals("free");
    }

}
