package com.calblay.catering_api.users.application.usecase;

import org.springframework.stereotype.Service;
import com.calblay.catering_api.users.domain.model.User;
import com.calblay.catering_api.users.application.port.out.UserRepositoryPort;


@Service
public class UpdateProfileUseCase
{
    private final UserRepositoryPort userRepositoryPort;

    public UpdateProfileUseCase(UserRepositoryPort userRepositoryPort)
    {
        this.userRepositoryPort = userRepositoryPort;
    }

    public UpdateProfileResult updateProfile(
        String usuario,
        String nombre,
        String usuarioNuevo,
        String rol,
        String correo,
        String password
    )
    {
        User user = userRepositoryPort.getByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado para modificar"));

        if(nombre != null && !nombre.isBlank() && !nombre.equals(user.getNombre()))
        {
            user.setNombre(nombre);
        }
        
        if(usuarioNuevo != null && !usuarioNuevo.isBlank() && !usuarioNuevo.equals(user.getUsuario()))
        {
            user.setUsuario(usuarioNuevo);
        }

        if(rol != null && !rol.isBlank() && !rol.equals(user.getRol()))
        {
            user.setRol(rol);
        }

        if(correo != null && !correo.isBlank() && !correo.equals(user.getCorreo()))
        {
            user.setCorreo(correo);
        }

        if(password != null && !password.isBlank() && !password.equals(user.getPassword()))
        {
            user.setPassword(password);
        }

        userRepositoryPort.save(user);

        return new UpdateProfileResult(
            user.getNombre(),
            user.getUsuario(),
            user.getRol(),
            user.getCorreo(),
            user.getPassword()
        );
    }
}