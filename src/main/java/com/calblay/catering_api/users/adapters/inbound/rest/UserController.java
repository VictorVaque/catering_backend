package com.calblay.catering_api.users.adapters.inbound.rest;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.calblay.catering_api.users.application.usecase.CreateUserResult;
import com.calblay.catering_api.users.application.usecase.CreateUserUseCase;

import com.calblay.catering_api.users.application.usecase.GetProfileUseCase;
import com.calblay.catering_api.users.application.usecase.GetProfileResult;

import com.calblay.catering_api.users.application.usecase.UpdateProfileUseCase;
import com.calblay.catering_api.users.application.usecase.UpdateProfileResult;

import com.calblay.catering_api.users.application.usecase.GetAllUsernamesUseCase;
import com.calblay.catering_api.users.application.usecase.GetAllUsernamesResult;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final GetProfileUseCase getProfileService;
    private final UpdateProfileUseCase updateProfileService;
    private final GetAllUsernamesUseCase getAllUsernamesService;
    private final CreateUserUseCase createUserUseCase;

    public UserController(
            GetProfileUseCase getProfileService,
            UpdateProfileUseCase updateProfileService,
            GetAllUsernamesUseCase getAllUsernamesService,
            CreateUserUseCase createUserUseCase) {
        this.getProfileService = getProfileService;
        this.updateProfileService = updateProfileService;
        this.getAllUsernamesService = getAllUsernamesService;
        this.createUserUseCase = createUserUseCase;
    }

    @GetMapping("/{usuario}") // GET -- coge la info de alguien
    public ResponseEntity<?> getProfile(@PathVariable("usuario") String usuario) {
        try {
            GetProfileResult resultado = this.getProfileService.getProfile(usuario);
            GetProfileResponse response = new GetProfileResponse(
                    resultado.getNombre(),
                    resultado.getUsuario(),
                    resultado.getRol(),
                    resultado.getCorreo());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{usuario}") // PUT -- actualiza la info de alguien
    public ResponseEntity<?> updateProfile(@PathVariable("usuario") String usuario,
            @RequestBody UpdateProfileRequest request) {
        try {
            UpdateProfileResult resultado = this.updateProfileService.updateProfile(
                    usuario,
                    request.getNombre(),
                    request.getUsuario(),
                    request.getRol(),
                    request.getCorreo(),
                    request.getPassword());
            UpdateProfileResponse response = new UpdateProfileResponse(
                    resultado.getNombre(),
                    resultado.getUsuario(),
                    resultado.getRol(),
                    resultado.getCorreo(),
                    resultado.getPassword());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/list") // GET -- coge la info de todos los usuarios
    public ResponseEntity<?> getAllUsernames() {
        try {
            GetAllUsernamesResult resultado = this.getAllUsernamesService.getAllUsernames();
            GetAllUsernamesResponse response = new GetAllUsernamesResponse(
                    resultado.getUsernames());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Error al obtener a los usuarios; " + e.getMessage());
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request) {
        try {
            CreateUserResult resultado = createUserUseCase.createUser(
                    request.getNombre(),
                    request.getUsuario(),
                    request.getCorreo(),
                    request.getPassword(),
                    request.getConfirmPassword(),
                    request.getRol());
            CreateUserResponse response = new CreateUserResponse(
                    resultado.getIdUsuario(),
                    resultado.getUsuario(),
                    resultado.getCorreo(),
                    resultado.getNombre(),
                    resultado.getRol());
            return ResponseEntity.status(201).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new ErrorResponse(400, e.getMessage()));
        }
    }

    // NUEVO - solo esto es tuyo
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        String mensajeError = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(400).body(new ErrorResponse(400, mensajeError));
    }
}