package com.calblay.catering_api.users.application.usecase;

import com.calblay.catering_api.users.application.port.out.UserRepositoryPort;
import com.calblay.catering_api.users.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public CreateUserUseCase(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public CreateUserResult createUser(String nombre, String usuario, String correo,
            String password, String confirmPassword, String rol) {

        // Validar que las contraseñas coinciden
        if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Las contraseñas no coinciden");
        }

        // Validar usuario único
        if (userRepositoryPort.getByUsuario(usuario).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        // Validar correo único
        if (userRepositoryPort.findByEmail(correo).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Crear el dominio (sin id, lo asigna la BD)
        User newUser = new User(null,usuario, correo, password, nombre, rol);

        // Guardar y devolver resultado
        User saved = userRepositoryPort.save(newUser);

        return new CreateUserResult(
                saved.getIdUsuario(),
                saved.getUsuario(),
                saved.getCorreo(),
                saved.getNombre(),
                saved.getRol());
    }
}