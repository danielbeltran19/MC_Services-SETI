package com.franquicia.franquicia_api.Franchises.aplication.port.in;

import com.franquicia.franquicia_api.Franchises.domain.models.FranquiciaRequestDTO;
import com.franquicia.franquicia_api.Franchises.insfrastructure.out.entity.Franquicia;
import reactor.core.publisher.Mono;

public interface FranquiciaInRepository {
    Mono<Franquicia> saveFranquicia(FranquiciaRequestDTO franquicia);
    Mono<Franquicia> updateFranquicia(FranquiciaRequestDTO franquicia);
}
