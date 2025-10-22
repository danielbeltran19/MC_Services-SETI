package com.franquicia.franquicia_api.Franchises.aplication.services;

import com.franquicia.franquicia_api.Franchises.aplication.port.in.FranquiciaInRepository;
import com.franquicia.franquicia_api.Franchises.aplication.port.out.FranquiciaOutRepository;
import com.franquicia.franquicia_api.Franchises.domain.models.FranquiciaRequestDTO;
import com.franquicia.franquicia_api.Franchises.insfrastructure.out.entity.Franquicia;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FranquiciaServices implements FranquiciaInRepository {

    private final FranquiciaOutRepository franquiciaRepository;

    public FranquiciaServices(FranquiciaOutRepository franquiciaRepository) {
        this.franquiciaRepository = franquiciaRepository;
    }

    @Override
    public Mono<Franquicia> saveFranquicia(FranquiciaRequestDTO franquicia) {
        if (franquicia.getNombre()==null){
            throw new IllegalArgumentException("El nombre es obligatorio, no puede ser null");
        }
        return franquiciaRepository.save(franquicia);
    }

    @Override
    public Mono<Franquicia> updateFranquicia(FranquiciaRequestDTO franquicia) {
        if (franquicia.getId()==null){
            throw new IllegalArgumentException("El id no puede ser nul");
        }
        return franquiciaRepository.updateFranquicia(franquicia);
    }
}
