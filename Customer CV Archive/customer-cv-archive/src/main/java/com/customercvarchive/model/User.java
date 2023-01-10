package com.customercvarchive.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "username")
    private String username;

    //@Transient
    @Column(name = "password")
    private String password;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role")
    private Role role;

    @Transient // geçici işlemler için kullanılır
    private String token; // Jwt için eklendi
}
