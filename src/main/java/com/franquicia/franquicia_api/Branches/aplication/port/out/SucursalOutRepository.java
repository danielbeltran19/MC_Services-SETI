package com.franquicia.franquicia_api.Branches.aplication.port.out;

import com.franquicia.franquicia_api.Branches.domain.models.SucursalRequestDTO;
import com.franquicia.franquicia_api.Branches.insfrastructure.out.entity.Sucursal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SucursalOutRepository {
    Mono<Sucursal> save(SucursalRequestDTO sucursal);
    Flux<Sucursal> findByFranquiciaId(Long franquiciaId);
    Mono<Sucursal> updateSucursal(SucursalRequestDTO sucursal);
}
