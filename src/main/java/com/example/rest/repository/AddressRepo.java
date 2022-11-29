package com.example.rest.repository;

import com.example.rest.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AddressRepo extends JpaRepository<AddressEntity, Integer> {
    AddressEntity findByUserId(Integer id);
}
