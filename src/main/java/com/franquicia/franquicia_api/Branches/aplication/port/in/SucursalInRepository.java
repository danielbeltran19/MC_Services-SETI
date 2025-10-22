package com.franquicia.franquicia_api.Branches.aplication.port.in;

import com.franquicia.franquicia_api.Branches.domain.models.SucursalRequestDTO;
import com.franquicia.franquicia_api.Branches.insfrastructure.out.entity.Sucursal;
import reactor.core.publisher.Mono;

public interface SucursalInRepository {
    Mono<Sucursal> saveSucursal(SucursalRequestDTO sucursal);
    Mono<Sucursal> updateSucursal(SucursalRequestDTO sucursal);
}
