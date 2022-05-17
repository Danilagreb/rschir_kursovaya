package com.example.rest.service;

import com.example.rest.entity.RoleEntity;
import com.example.rest.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;

    public RoleEntity findOrCreateRole(String name) {
        RoleEntity role = roleRepo.findByName(name);
        if(role == null) {
            RoleEntity newRole = new RoleEntity();
            newRole.setName(name);
            roleRepo.save(newRole);
            return newRole;
        }
        return role;

    }
}
