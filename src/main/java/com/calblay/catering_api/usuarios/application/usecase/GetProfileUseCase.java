package com.calblay.catering_api.usuarios.application.usecase;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.calblay.catering_api.usuarios.domain.model.User;
import com.calblay.catering_api.usuarios.application.port.out.UserRepositoryPort;

@Service
public class GetProfileUseCase
{
    private final UserRepositoryPort userRepositoryPort;

    public GetProfileUseCase(UserRepositoryPort userRepositoryPort)
    {
        this.userRepositoryPort = userRepositoryPort;
    }

    //Obtenemos todos los datos de un usuario a partir de su username
    public GetProfileResult getProfile(String usuario)
    {
        User user = userRepositoryPort.getByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        //NO HE AÑADIDO NINGUN GESTION DE ERROR
        return new GetProfileResult(
            user.getNombre(),
            user.getUsuario(),
            user.getRol(),
            user.getCorreo()
        );
    }
}