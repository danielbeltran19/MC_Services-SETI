package com.franquicia.franquicia_api.Franchises.insfrastructure.out.persistense;

import com.franquicia.franquicia_api.Franchises.aplication.port.out.FranquiciaOutRepository;
import com.franquicia.franquicia_api.Franchises.domain.models.FranquiciaRequestDTO;
import com.franquicia.franquicia_api.Franchises.insfrastructure.out.entity.Franquicia;
import com.franquicia.franquicia_api.Franchises.insfrastructure.out.mapper.FranquiciaMapper;
import com.franquicia.franquicia_api.Franchises.insfrastructure.out.repository.FranquiciaJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class FranquiciaRepositoryImpl implements FranquiciaOutRepository {
    private final FranquiciaJpaRepository franquiciaJpaRepository;
    private final FranquiciaMapper franquiciaMapper;

    private static final Logger log = LoggerFactory.getLogger(FranquiciaRepositoryImpl.class);

    public FranquiciaRepositoryImpl(FranquiciaJpaRepository franquiciaJpaRepository , FranquiciaMapper franquiciaMapper) {
        this.franquiciaJpaRepository = franquiciaJpaRepository;
        this.franquiciaMapper = franquiciaMapper;
    }

    @Override
    public Mono<Franquicia> save(FranquiciaRequestDTO franquiciaRequestDTO) {
        Franquicia franquiciaEntity = franquiciaMapper.toEntity(franquiciaRequestDTO);
        return franquiciaJpaRepository.save(franquiciaEntity)
                .doOnNext(s -> log.info("Franquicia guardada con ID: {}", s.getId()))
                .doOnError(e -> log.error("Error al guardar Franquicia: {}", e.getMessage(), e));
    }

    @Override
    public Mono<Franquicia> findById(Long id) {
        return franquiciaJpaRepository.findById(id)
                .doOnNext(f -> log.info("Franquicia encontrada: {}", f.getNombre()))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Franquicia con ID {} no encontrada", id);
                    return Mono.empty();
                }))
                .doOnError(e -> log.error("Error al buscar Franquicia por ID: {}", e.getMessage(), e));
    }

    @Override
    public Mono<Franquicia> updateFranquicia(FranquiciaRequestDTO franquicia) {
        return franquiciaJpaRepository.findById(franquicia.getId())
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "Franquicia con ID " + franquicia.getId() + " no encontrada")))
                .flatMap(existingSucursal -> {
                    existingSucursal.setNombre(franquicia.getNombre());
                    return franquiciaJpaRepository.save(existingSucursal);
                })
                .doOnNext(s -> log.info("Franquicia actualizada: {} -> {}", s.getId(), s.getNombre()))
                .doOnError(e -> log.error("Error al actualizar sucursal: {}", e.getMessage(), e));
    }
}
