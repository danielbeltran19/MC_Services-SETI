package com.franquicia.franquicia_api.Branches.aplication.services;

import com.franquicia.franquicia_api.Branches.aplication.port.in.SucursalInRepository;
import com.franquicia.franquicia_api.Branches.aplication.port.out.SucursalOutRepository;
import com.franquicia.franquicia_api.Branches.domain.models.SucursalRequestDTO;
import com.franquicia.franquicia_api.Branches.insfrastructure.out.entity.Sucursal;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SucursalService implements SucursalInRepository {
    private final SucursalOutRepository sucursalOutRepository;

    public SucursalService(SucursalOutRepository sucursalOutRepository) {
        this.sucursalOutRepository = sucursalOutRepository;
    }

    @Override
    public Mono<Sucursal> saveSucursal(SucursalRequestDTO sucursal) {

        if (sucursal.getNombre()==null){
            throw new IllegalArgumentException("El nombre es obligatorio, no puede ser null");
        } else if (sucursal.getFranquiciaId()==null) {
            throw new IllegalArgumentException("La franquicia es obligatoria, no puede ser null");
        }
        return sucursalOutRepository.save(sucursal);
    }

    @Override
    public Mono<Sucursal> updateSucursal(SucursalRequestDTO sucursal) {
        if (sucursal.getId()==null){
            throw new IllegalArgumentException("El id es requerido para la actualizacion.");
        }
        return sucursalOutRepository.updateSucursal(sucursal);
    }
}
