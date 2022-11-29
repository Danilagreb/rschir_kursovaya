package com.example.rest.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;


@Entity
@Table(name = "certificates")
@Getter
@Setter
@ToString
public class CertificateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "to_user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private UserEntity toUser;
    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "from_user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private UserEntity fromUser;
    private int price;
    @Column(name = "date_create")
    private String dateCreate;

}
