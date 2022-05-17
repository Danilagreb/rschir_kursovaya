package com.example.rest.repository;

import com.example.rest.entity.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CertificateRepo extends JpaRepository<CertificateEntity, UUID> {

}
