package com.calblay.catering_api.usuarios.adapters.inbound.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.calblay.catering_api.usuarios.application.usecase.GetProfileUseCase;
import com.calblay.catering_api.usuarios.application.usecase.GetProfileResult;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserController {

    private final GetProfileUseCase profileService;

    public UserController(GetProfileUseCase profileService)
    {
        this.profileService = profileService;
    }
    //CONSULTA DE PERFIL DE USUARIO
    @GetMapping("/{usuario}")
    public ResponseEntity<?> getProfile(@PathVariable("usuario") String usuario)
    {
        try
        {
            GetProfileResult resultado = this.profileService.getProfile(usuario);
            GetProfileResponse response = new GetProfileResponse(
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