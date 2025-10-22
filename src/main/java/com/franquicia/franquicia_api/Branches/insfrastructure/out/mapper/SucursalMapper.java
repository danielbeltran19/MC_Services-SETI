package com.franquicia.franquicia_api.Branches.insfrastructure.out.mapper;

import com.franquicia.franquicia_api.Branches.domain.models.SucursalRequestDTO;
import com.franquicia.franquicia_api.Branches.insfrastructure.out.entity.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {

    public static Sucursal toEntity(SucursalRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Sucursal entity = new Sucursal();
        entity.setNombre(dto.getNombre());
        entity.setFranquiciaId(dto.getFranquiciaId());
        return entity;
    }

}
