package com.example.rest.repository;

import com.example.rest.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findByName(String name);
}
