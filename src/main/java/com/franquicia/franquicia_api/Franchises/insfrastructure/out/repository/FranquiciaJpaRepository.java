package com.franquicia.franquicia_api.Franchises.insfrastructure.out.repository;

import com.franquicia.franquicia_api.Branches.insfrastructure.out.entity.Sucursal;
import com.franquicia.franquicia_api.Franchises.insfrastructure.out.entity.Franquicia;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface FranquiciaJpaRepository extends ReactiveCrudRepository<Franquicia, Long> {
    Mono<Franquicia> findById(Long id);
}
