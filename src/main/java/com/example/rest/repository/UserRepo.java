package com.example.rest.repository;

import com.example.rest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findAllByFirstNameContainingAndLastNameContainingAndUsernameContaining(String firstName, String lastName, String username);
    UserEntity findByUsername(String username);
    default List<UserEntity> filterByFields(UserEntity user) {
        return findAllByFirstNameContainingAndLastNameContainingAndUsernameContaining(user.getFirstName(), user.getLastName(), user.getUsername());
    }

}
