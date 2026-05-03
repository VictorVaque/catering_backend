package com.calblay.catering_api.usuarios.adapters.outbound.persistence;

import jakarta.persistence.*; 
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class UserEntity {
    //Atributos de la tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    private String usuario;
    @Column(name = "correo")
    private String email;
    private String password;
    private String nombre;
    private String rol;
}