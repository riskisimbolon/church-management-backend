package com.example.gmahk.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "absensi")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Absensi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kegiatan_id")
    private Kegiatan kegiatan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jemaat_id")
    private Jemaat jemaat;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_hadir", columnDefinition = "ENUM('hadir','tidak')")
    private StatusHadir statusHadir;

    public enum StatusHadir { hadir, tidak }
}
