package com.franquicia.franquicia_api.Branches.insfrastructure.out.repository;

import com.franquicia.franquicia_api.Branches.insfrastructure.out.entity.Sucursal;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SucursalJpaRepository extends ReactiveCrudRepository<Sucursal, Long> {
    Flux<Sucursal> findByFranquiciaId(Long franquiciaId);
}
