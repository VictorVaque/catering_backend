package com.calblay.catering_api.usuarios.adapters.inbound.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.calblay.catering_api.usuarios.application.usecase.LoginUseCase;
import com.calblay.catering_api.usuarios.application.usecase.LoginResult;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserController {

    private final ProfileUseCase profileService;

    public ProfileController(ProfileUseCase profileService)
    {
        this.profileService = profileService;
    }
    //CONSULTA DE PERFIL DE USUARIO
    @GetMapping("/{username}")
    public ResponseEntity<?> getProfile(@RequestBody UserRequest request)
    {
        try
        {
            String usuario = request.getUsuario();
            PerfilResult resultado = PerfilUseCase.getProfile(usuario);
            PerfilResponse response = new PerfilResponse
            (
                resultado.getUsuario(),
                resultado.getNombre(),
                resultado.getCorreo(),
                resultado.getRol()
            )

            return ResponseEntity.ok(response);         
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }
}