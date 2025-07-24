package com.showcase.application.springbootbackend.models.security;

import com.showcase.application.springbootbackend.models.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "security_user")
public class User extends BaseModel {

    private String name;
    @Column(unique = true)
    private String email;
    private String phoneNumber;

}
