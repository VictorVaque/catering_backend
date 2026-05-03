package com.calblay.catering_api.usuarios.adapters.inbound.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.calblay.catering_api.usuarios.application.usecase.GetProfileUseCase;
import com.calblay.catering_api.usuarios.application.usecase.GetProfileResult;

import com.calblay.catering_api.usuarios.application.usecase.UpdateProfileUseCase;
import com.calblay.catering_api.usuarios.application.usecase.UpdateProfileResult;

import java.util.List;
import com.calblay.catering_api.usuarios.application.usecase.GetAllUsernamesUseCase;
import com.calblay.catering_api.usuarios.application.usecase.GetAllUsernamesResult;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserController {

    private final GetProfileUseCase getProfileService;
    private final UpdateProfileUseCase updateProfileService;
    private final GetAllUsernamesUseCase getAllUsernamesService;

    public UserController(
        GetProfileUseCase getProfileService,
        UpdateProfileUseCase updateProfileService,
        GetAllUsernamesUseCase getAllUsernamesService
    )
    {
        this.getProfileService = getProfileService;
        this.updateProfileService = updateProfileService;
        this.getAllUsernamesService = getAllUsernamesService;
    }

    //CONSULTA DE PERFIL DE USUARIO
    @GetMapping("/perfil/{usuario}")
    public ResponseEntity<?> getProfile(@PathVariable("usuario") String usuario)
    {
        try
        {
            GetProfileResult resultado = this.getProfileService.getProfile(usuario);
            GetProfileResponse response = new GetProfileResponse(
               //EL ORDEN QUE LLAMAMOS AQUI TIENE QUE COINCIDIR CON EL ORDEN DE LOS PARAMETROS DE GetProfileResult.java
                resultado.getNombre(),                
                resultado.getUsuario(),
                resultado.getRol(),
                resultado.getCorreo()
            );

            return ResponseEntity.ok(response);         
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }

    //MODIFICA LOS DATOS DEL USUARIO
    @PutMapping("/perfil/modificar/{usuario}")
    public ResponseEntity<?> updateProfile(@PathVariable("usuario") String usuario, @RequestBody UpdateProfileRequest request)
    {
        try
        {
            UpdateProfileResult resultado = this.updateProfileService.updateProfile(usuario,
                request.getNombre(),
                request.getUsuario(),
                request.getRol(),
                request.getCorreo(),
                request.getPassword()
            );
            UpdateProfileResponse response = new UpdateProfileResponse(
            //Queremos mostrar todos los datos actualizados en pantalla
                resultado.getNombre(),                
                resultado.getUsuario(),
                resultado.getRol(),
                resultado.getCorreo(),
                resultado.getPassword()
            );

            return ResponseEntity.ok(response);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/lista-usuarios")
    public ResponseEntity<?> getAllUsernames()
    {
        try
        {
            GetAllUsernamesResult resultado = this.getAllUsernamesService.getAllUsernames();
            GetAllUsernamesResponse response = new GetAllUsernamesResponse(
                resultado.getUsernames()
            );
            return ResponseEntity.ok(response);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(500).body("Error al obtener a los usuarios; " + e.getMessage());
        }
    }
}