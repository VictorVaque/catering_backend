package com.calblay.catering_api.events.adapters.inbound.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateEventInfoRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotNull(message = "La fecha no puede estar vacía")
    @FutureOrPresent(message = "La fecha debe ser actual o futura")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm") // Formato exigido en la historia
    private LocalDateTime fechahora;

    @NotNull(message = "El recinto es obligatorio")
    private Integer idrecinto;

    @NotNull(message = "El número de comensales es obligatorio")
    @Positive(message = "El número de comensales debe ser mayor a 0")
    private Integer comensales;
}