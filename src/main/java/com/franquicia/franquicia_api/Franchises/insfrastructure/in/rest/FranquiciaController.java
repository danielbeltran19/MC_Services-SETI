package com.franquicia.franquicia_api.Franchises.insfrastructure.in.rest;

import com.franquicia.franquicia_api.Branches.aplication.port.in.SucursalInRepository;
import com.franquicia.franquicia_api.Branches.domain.models.SucursalRequestDTO;
import com.franquicia.franquicia_api.Franchises.aplication.port.in.FranquiciaInRepository;
import com.franquicia.franquicia_api.Franchises.domain.models.FranquiciaRequestDTO;
import com.franquicia.franquicia_api.shared.util.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${api.base.path}/franquicias")
public class FranquiciaController {
    private final FranquiciaInRepository franquiciaService;

    public FranquiciaController(FranquiciaInRepository franquiciaService) {
        this.franquiciaService = franquiciaService;
    }


    @PostMapping("/create")
    public Mono<ResponseEntity<ResponseMessage>> createFranquicia(@RequestBody FranquiciaRequestDTO request) {
        return franquiciaService.saveFranquicia(request)
                .map(saved -> {
                    ResponseMessage response = new ResponseMessage(
                            HttpStatus.CREATED.name(),
                            HttpStatus.CREATED.value(),
                            "Franquicia creada correctamente"
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
    public Mono<ResponseEntity<ResponseMessage>> updateFranquicia(@RequestBody FranquiciaRequestDTO request) {
        return franquiciaService.updateFranquicia(request)
                .map(updated -> ResponseEntity.ok(
                        new ResponseMessage(
                                HttpStatus.OK.name(),
                                HttpStatus.OK.value(),
                                "Franquicia actualizada correctamente con ID: " + updated.getId()
                        )))
                .onErrorResume(error -> Mono.just(
                        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ResponseMessage(
                                        HttpStatus.INTERNAL_SERVER_ERROR.name(),
                                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                        "Error al actualizar la franquicia: " + error.getMessage()
                                ))
                ));
    }
}
