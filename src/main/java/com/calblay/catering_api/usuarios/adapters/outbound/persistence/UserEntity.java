package com.calblay.catering_api.usuarios.adapters.outbound.persistence;
//tengo dudas sobre que se use jakarta en versiones nuevas de spring (revisar)
import jakarta.persistence.*; 
import lombok.Data;

@Data
@Entity
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