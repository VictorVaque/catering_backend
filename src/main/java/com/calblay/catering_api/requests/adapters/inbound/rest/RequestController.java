package com.calblay.catering_api.requests.adapters.inbound.rest;

import com.calblay.catering_api.requests.adapters.inbound.rest.GetAllRequestsResponse;
import com.calblay.catering_api.requests.adapters.inbound.rest.RequestWithEventResponse;
import com.calblay.catering_api.requests.application.usecase.GetAllRequestsResult;
import com.calblay.catering_api.requests.application.usecase.GetAllRequestsUseCase;
import com.calblay.catering_api.requests.application.usecase.GetRequestsListResult;
import com.calblay.catering_api.requests.application.usecase.GetRequestsListUseCase;
import com.calblay.catering_api.requests.application.usecase.UpdateRequestStatusUseCase;
import com.calblay.catering_api.requests.application.usecase.GetRequestDetailResult;
import com.calblay.catering_api.requests.application.usecase.GetRequestDetailUseCase;
import com.calblay.catering_api.requests.application.usecase.CreateRequestUseCase;
import com.calblay.catering_api.requests.application.usecase.CreateRequestResult;
import com.calblay.catering_api.requests.adapters.inbound.rest.RequestStatusUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "*")
public class RequestController {

    private final GetAllRequestsUseCase getAllRequestsUseCase;
    private final GetRequestsListUseCase getRequestsListUseCase;
    private final GetRequestDetailUseCase getRequestDetailUseCase;
    private final UpdateRequestStatusUseCase updateRequestStatusUseCase;
    private final CreateRequestUseCase createRequestUseCase;

    public RequestController(GetAllRequestsUseCase getAllRequestsUseCase, GetRequestsListUseCase getRequestsListUseCase,
            GetRequestDetailUseCase getRequestDetailUseCase, UpdateRequestStatusUseCase updateRequestStatusUseCase, CreateRequestUseCase createRequestUseCase) {
        this.getAllRequestsUseCase = getAllRequestsUseCase;
        this.getRequestsListUseCase = getRequestsListUseCase;
        this.getRequestDetailUseCase = getRequestDetailUseCase;
        this.updateRequestStatusUseCase = updateRequestStatusUseCase;
         this.createRequestUseCase = createRequestUseCase;
    }

    // Actualizarlo para que no sea con el header
    @GetMapping("/event/{idEvento}")
    public ResponseEntity<?> getRequestsList(@PathVariable("idEvento") Integer idEvento) {
        if (idEvento == null) {
            return ResponseEntity.badRequest().body("El parámetro idEvento es obligatorio");
        }

        try {
            GetRequestsListResult result = getRequestsListUseCase.getRequestsList(idEvento);
            List<RequestSummaryResponse> requests = result.getRequests().stream()
                    .map(r -> new RequestSummaryResponse(
                            r.getIdPeticion(),
                            r.getTitulo(),
                            r.getTipo(),
                            r.getFechaCreacion(),
                            r.getEstado()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new GetRequestsListResponse(requests));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener las peticiones del evento: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllRequests() {
        try {
            GetAllRequestsResult result = getAllRequestsUseCase.getAllRequests();
            List<RequestWithEventResponse> requests = result.getRequests().stream()
                    .map(r -> new RequestWithEventResponse(
                            r.getIdPeticion(),
                            r.getResponsable(),
                            r.getCreador(),
                            r.getIdEvento(),
                            r.getFechaCreacion(),
                            r.getEstado(),
                            r.getTitulo(),
                            r.getDescripcion(),
                            r.getTipo()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new GetAllRequestsResponse(requests));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener todas las peticiones: " + e.getMessage());
        }
    }

    @GetMapping("/{idPeticion}")
    public ResponseEntity<?> getRequestDetail(@PathVariable("idPeticion") Integer idPeticion) {
        if (idPeticion == null) {
            return ResponseEntity.badRequest().body("El parámetro idPeticion es obligatorio");
        }

        try {
            GetRequestDetailResult result = getRequestDetailUseCase.getRequestDetail(idPeticion);
            return result.getRequest()
                    .<ResponseEntity<?>>map(request -> ResponseEntity.ok(new RequestDetailResponse(
                        request.getIdPeticion(),
                        request.getResponsable(),
                        request.getCreador(),
                        request.getFechaCreacion(),
                        request.getEstado(),
                        request.getTitulo(),
                        request.getDescripcion(),
                        request.getTipo(),
                        request.getIdEvento(),
                        request.getNombreEvento())))
                    .orElseGet(() -> ResponseEntity.status(404).body("Petición no encontrada"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener el detalle de la petición: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createRequest(@RequestBody CreateRequestRequest request) {
        try {
            CreateRequestResult result = createRequestUseCase.createRequest(request);

            return ResponseEntity.ok(new CreateRequestResponse(
                    result.getMensaje(),
                    result.getIdPeticion()
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRequestStatus(
            @PathVariable("id") Integer idPeticion,
            @RequestBody RequestStatusUpdate requestBody) {
   
        try {
            updateRequestStatusUseCase.updateStatus(idPeticion, requestBody.getStatus());

            return ResponseEntity.ok()
                    .body(requestBody.getStatus());

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar el estado de la petición: " + e.getMessage());
        }
    }

}
