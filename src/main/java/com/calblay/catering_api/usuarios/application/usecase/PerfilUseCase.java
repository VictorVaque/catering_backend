package com.calblay.catering_api.usuarios.application.usecase;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class PerfilUseCase
{
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public PerfilUseCase(UsuarioRepositoryPort usuarioRepositoryPort)
    {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    //Obtenemos todos los datos de un usuario a partir de su username
    public ProfileResponse getProfile(String usuario)
    {
        Usuario user = usuarioRepositoryPort.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        //NO HE AÑADIDO NINGUN GESTION DE ERROR
        return new PerfilResult
        (
            user.getNombre(),
            user.getUsuario(),
            user.getRol(),
            user.getCorreo()
        );
    }
}