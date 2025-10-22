package com.franquicia.franquicia_api.Franchises.aplication.port.out;

import com.franquicia.franquicia_api.Branches.insfrastructure.out.entity.Sucursal;
import com.franquicia.franquicia_api.Franchises.domain.models.FranquiciaRequestDTO;
import com.franquicia.franquicia_api.Franchises.insfrastructure.out.entity.Franquicia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranquiciaOutRepository {
    Mono<Franquicia> save(FranquiciaRequestDTO franquicia);
    Mono<Franquicia> findById(Long id);
    Mono<Franquicia> updateFranquicia(FranquiciaRequestDTO franquicia);

}
