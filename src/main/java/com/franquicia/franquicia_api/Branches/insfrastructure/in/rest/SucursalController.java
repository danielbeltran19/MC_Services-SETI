package com.franquicia.franquicia_api.Branches.insfrastructure.in.rest;

import com.franquicia.franquicia_api.Branches.aplication.port.in.SucursalInRepository;
import com.franquicia.franquicia_api.Branches.domain.models.SucursalRequestDTO;
import com.franquicia.franquicia_api.shared.util.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${api.base.path}/sucursales")
public class SucursalController {

    private final SucursalInRepository sucursalService;

    public SucursalController(SucursalInRepository sucursalService) {
        this.sucursalService = sucursalService;
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<ResponseMessage>> createSucursal(@RequestBody SucursalRequestDTO request) {
        return sucursalService.saveSucursal(request)
                .map(saved -> {
                    ResponseMessage response = new ResponseMessage(
                            HttpStatus.CREATED.name(),
                            HttpStatus.CREATED.value(),
                            "Sucursal creada correctamente"
                    );
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                })
                .onErrorResume(error -> {
                    ResponseMessage errorResponse = new ResponseMessage(
                            HttpStatus.INTERNAL_SERVER_ERROR.name(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Error al crear la sucursal: " + error.getMessage()
                    );
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
                });
    }

    @PutMapping("/update")
    public Mono<ResponseEntity<ResponseMessage>> updateSucursal(@RequestBody SucursalRequestDTO request) {
        return sucursalService.updateSucursal(request)
                .map(updated -> ResponseEntity.ok(
                        new ResponseMessage(
                                HttpStatus.OK.name(),
                                HttpStatus.OK.value(),
                                "Sucursal actualizada correctamente con ID: " + updated.getId()
                        )))
                .onErrorResume(error -> Mono.just(
                        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ResponseMessage(
                                        HttpStatus.INTERNAL_SERVER_ERROR.name(),
                                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                        "Error al actualizar la sucursal: " + error.getMessage()
                                ))
                ));
    }

}
