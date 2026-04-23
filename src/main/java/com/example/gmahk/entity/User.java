package com.example.gmahk.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String nama;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 255)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "jemaat_id")
    private Integer jemaatId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('aktif','nonaktif') DEFAULT 'aktif'")
    private StatusUser status = StatusUser.aktif;

    public enum StatusUser {
        aktif, nonaktif
    }
}
