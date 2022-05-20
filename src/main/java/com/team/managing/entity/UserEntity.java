package com.team.managing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, length = 32)
    private String login;

    @Column(length = 32)
    private String password;

    @Column(unique = true, length = 32)
    private String email;

    @Column(length = 32)
    private String firstName;

    @Column(length = 32)
    private String lastName;

    @Column(length = 32)
    private Date birthday;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private RoleEntity roleEntity;

    public UserEntity(String login,
                      String password,
                      String email,
                      String firstName,
                      String lastName,
                      Date birthday,
                      RoleEntity roleEntity) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.roleEntity = roleEntity;
    }
}