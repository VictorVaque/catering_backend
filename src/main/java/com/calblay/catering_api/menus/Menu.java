package com.calblay.catering_api.menus;

import com.calblay.catering_api.usuarios.Usuario;
import com.calblay.catering_api.menus.enums.EstadoRegistro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "menus")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_menu")
    private Long idMenu;

    //FK al creador del menú
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_creador")
    private Usuario creador;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(name = "imagen_url", nullable = false, columnDefinition = "TEXT")
    private String imagenUrl;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoRegistro estado;
}