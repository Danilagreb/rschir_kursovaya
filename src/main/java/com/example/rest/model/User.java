package com.example.rest.model;

import com.example.rest.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;

    // не возвращаю пароль, кол-во денег, роли

    public User() {}

    public static User toModel(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setUsername(userEntity.getUsername());
        user.setEmail(userEntity.getEmail());
        user.setPhone(userEntity.getPhone());
        return user;
    }
    public static List<User> toModel(List<UserEntity> userEntities) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < userEntities.size(); i++) {
            UserEntity user = userEntities.get(i);
            users.add(toModel(user));
        }
        return users;
    }
}

