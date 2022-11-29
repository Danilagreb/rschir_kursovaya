package com.example.rest.repository;

import com.example.rest.entity.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CertificateRepo extends JpaRepository<CertificateEntity, Integer> {

}
