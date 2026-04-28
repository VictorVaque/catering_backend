package com.calblay.catering_api.usuarios.adapters.outbound.persistence;
//tengo dudas sobre que se use jakarta en versiones nuevas de spring (revisar)
import jakarta.persistence.*; 
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    @Column(name = "correo")
    private String email;
    private String password;
    private String nombre;
    private String rol;
}