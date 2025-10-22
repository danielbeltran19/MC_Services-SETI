package com.franquicia.franquicia_api.Products.insfrastructure.out.repository;

import com.franquicia.franquicia_api.Products.domain.models.ProductoRequestDTO;
import com.franquicia.franquicia_api.Products.insfrastructure.out.entity.Producto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductoJpaRepository  extends ReactiveCrudRepository<Producto, Long> {
    Flux<Producto> findBySucursalId(Long sucursalId);
    Mono<Producto> findBySucursalIdAndId(Long sucursalId, Long id);

    @Query("""
        SELECT p.id, p.nombre, p.stock, p.sucursal_id
        FROM productos p
        JOIN sucursales s ON p.sucursal_id = s.id
        WHERE s.franquicia_id = :franquiciaId
        AND p.stock = (
            SELECT MAX(p2.stock)
            FROM productos p2
            WHERE p2.sucursal_id = p.sucursal_id
        )
        """)
    Flux<Producto> findTopStockByFranquiciaId(Long franquiciaId);
}
