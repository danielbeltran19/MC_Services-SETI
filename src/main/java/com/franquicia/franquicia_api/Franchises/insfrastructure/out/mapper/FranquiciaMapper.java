package com.franquicia.franquicia_api.Franchises.insfrastructure.out.mapper;

import com.franquicia.franquicia_api.Franchises.domain.models.FranquiciaRequestDTO;
import com.franquicia.franquicia_api.Franchises.insfrastructure.out.entity.Franquicia;
import org.springframework.stereotype.Component;

@Component
public class FranquiciaMapper {
    public static Franquicia toEntity(FranquiciaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Franquicia entity = new Franquicia();
        entity.setNombre(dto.getNombre());
        return entity;
    }
}
