package com.franquicia.franquicia_api.Branches.insfrastructure.out.persistense;

import com.franquicia.franquicia_api.Branches.aplication.port.out.SucursalOutRepository;
import com.franquicia.franquicia_api.Branches.domain.models.SucursalRequestDTO;
import com.franquicia.franquicia_api.Branches.insfrastructure.out.entity.Sucursal;
import com.franquicia.franquicia_api.Branches.insfrastructure.out.mapper.SucursalMapper;
import com.franquicia.franquicia_api.Branches.insfrastructure.out.repository.SucursalJpaRepository;
import com.franquicia.franquicia_api.Franchises.insfrastructure.out.repository.FranquiciaJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class SucursalRepositoryImpl implements SucursalOutRepository {

    private final SucursalJpaRepository sucursalJpaRepository;
    private final SucursalMapper sucursalMapper;
    private final FranquiciaJpaRepository franquiciaJpaRepository;
    private static final Logger log = LoggerFactory.getLogger(SucursalRepositoryImpl.class);

    public SucursalRepositoryImpl(SucursalJpaRepository sucursalJpaRepository, SucursalMapper sucursalMapper,
                                  FranquiciaJpaRepository franquiciaJpaRepository) {
        this.sucursalJpaRepository = sucursalJpaRepository;
        this.sucursalMapper = sucursalMapper;
        this.franquiciaJpaRepository = franquiciaJpaRepository;
    }

    @Override
    public Mono<Sucursal> save(SucursalRequestDTO sucursalRequestDTO) {
        Sucursal sucursalEntity = SucursalMapper.toEntity(sucursalRequestDTO);

        return franquiciaJpaRepository.findById(sucursalEntity.getFranquiciaId())
                .switchIfEmpty(Mono.error(new RuntimeException("La franquicia con ID " + sucursalEntity.getFranquiciaId() + " no existe")))
                .flatMap(franquicia -> {
                    return sucursalJpaRepository.save(sucursalEntity)
                            .doOnNext(s -> log.info("Sucursal guardada con ID: {}", s.getId()))
                            .doOnError(e -> log.error("Error al guardar sucursal: {}", e.getMessage(), e));
                });
    }

    @Override
    public Flux<Sucursal> findByFranquiciaId(Long franquiciaId) {
        return sucursalJpaRepository.findByFranquiciaId(franquiciaId)
                .doOnSubscribe(s -> log.info("Buscando sucursales para franquiciaId: {}", franquiciaId))
                .doOnError(e -> log.error("Error al buscar sucursales: {}", e.getMessage(), e));
    }

    @Override
    public Mono<Sucursal> updateSucursal(SucursalRequestDTO sucursalDTO) {
        return sucursalJpaRepository.findById(sucursalDTO.getId())
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "Sucursal con ID " + sucursalDTO.getId() + " no encontrada")))
                .flatMap(existingSucursal -> {
                    existingSucursal.setNombre(sucursalDTO.getNombre());
                    return sucursalJpaRepository.save(existingSucursal);
                })
                .doOnNext(s -> log.info("Sucursal actualizada: {} -> {}", s.getId(), s.getNombre()))
                .doOnError(e -> log.error("Error al actualizar sucursal: {}", e.getMessage(), e));
    }
}
