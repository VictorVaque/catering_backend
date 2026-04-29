package com.calblay.catering_api.usuarios.adapters.inbound.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.calblay.catering_api.usuarios.application.usecase.ProfileUseCase;
import com.calblay.catering_api.usuarios.application.usecase.ProfileResult;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserController {

    private final ProfileUseCase profileService;

    public UserController(ProfileUseCase profileService)
    {
        this.profileService = profileService;
    }
    //CONSULTA DE PERFIL DE USUARIO
    @GetMapping("/{usuario}")
    public ResponseEntity<?> getProfile(@PathVariable("usuario") String usuario)
    {
        try
        {
            ProfileResult resultado = this.profileService.getProfile(usuario);
            ProfileResponse response = new ProfileResponse(
                resultado.getUsuario(),
                resultado.getNombre(),
                resultado.getCorreo(),
                resultado.getRol()
            );

            return ResponseEntity.ok(response);         
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }
}