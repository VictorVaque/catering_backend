package com.calblay.catering_api.users.adapters.outbound.persistence;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_idusuario_seq")
    @SequenceGenerator(
        name = "usuarios_idusuario_seq",
        sequenceName = "usuarios_idusuario_seq",
        allocationSize = 1
    )    
    @Column(name = "idusuario")
    private Integer idUsuario;

    private String usuario;
    @Column(name = "correo")
    private String email;
    private String password;
    private String nombre;
    private String rol;
}