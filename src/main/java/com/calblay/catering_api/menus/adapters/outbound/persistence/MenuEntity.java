package com.calblay.catering_api.menus.adapters.outbound.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menus")
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmenu")
    private Long idMenu;

    @Column(name = "idcreador")
    private Long idCreador;

    private String nombre;

    @Column(name = "imagenurl")
    private String imagenUrl;

    @Column(name = "fechacreacion")
    private LocalDateTime fechaCreacion;

    private String estado;
}
