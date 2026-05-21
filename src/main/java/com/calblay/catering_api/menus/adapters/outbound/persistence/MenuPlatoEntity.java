package com.calblay.catering_api.menus.adapters.outbound.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menuplato")
public class MenuPlatoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_menu_plato")
    private Long idMenuPlato;

    @Column(name = "idmenu")
    private Long idMenu;

    @Column(name = "idplato")
    private Long idPlato;

    private Integer orden;

    @Column(name = "ordenescrito")
    private String ordenEscrito;
}
