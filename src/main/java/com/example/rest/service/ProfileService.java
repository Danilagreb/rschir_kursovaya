package com.example.rest.service;

import com.example.rest.entity.AddressEntity;
import com.example.rest.entity.UserEntity;
import com.example.rest.repository.AddressRepo;
import com.example.rest.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AddressRepo addressRepo;

    public void updateMainInfo(UserEntity currentUser, UserEntity updatedUser) {
        currentUser.setPhone(updatedUser.getPhone());
        currentUser.setFirstName(updatedUser.getFirstName());
        currentUser.setLastName(updatedUser.getLastName());
        currentUser.setEmail(updatedUser.getEmail());
        userRepo.save(currentUser);
    }
    public void updateCash(UserEntity currentUser, UserEntity updatedUser) {
        currentUser.setCash(updatedUser.getCash() + currentUser.getCash());
        userRepo.save(currentUser);
    }
    public void removeCash(UserEntity currentUser, int money) {
        currentUser.setCash(currentUser.getCash() - money);
        userRepo.save(currentUser);
    }
    public void addCash(UserEntity user, int money) {
        user.setCash(user.getCash() + money);
        userRepo.save(user);
    }
    public void updateAddress(UserEntity currentUser, AddressEntity address) {
        AddressEntity newAddress = currentUser.getAddress();
        newAddress.setCountry(address.getCountry());
        newAddress.setCity(address.getCity());
        newAddress.setStreet(address.getStreet());
        newAddress.setHouse(address.getHouse());
        newAddress.setEntrance(address.getEntrance());
        newAddress.setFlat(address.getFlat());
        newAddress.setCommentary(address.getCommentary());
        newAddress.setUser(currentUser);
        addressRepo.save(newAddress);
    }
    public void removeAccount(UserEntity currentUser) {
        userRepo.deleteById(currentUser.getId());
    }
}
