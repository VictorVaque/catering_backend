package com.calblay.catering_api.events.adapters.outbound.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recintos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecintoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrecinto")
    private Integer idRecinto;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "nombre")
    private String nombre;
}