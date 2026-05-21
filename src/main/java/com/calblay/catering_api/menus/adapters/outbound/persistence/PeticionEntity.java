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
@Table(name = "peticiones")
public class PeticionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "peticiones_seq")
    @SequenceGenerator(name = "peticiones_seq", sequenceName = "peticiones_idpeticion_seq", allocationSize = 1)
    @Column(name = "idpeticion")
    private Integer idPeticion;

    private Integer responsable;
    private Integer creador;
    private String titulo;
    private String descripcion;
    private String tipo;
    private String estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
}
