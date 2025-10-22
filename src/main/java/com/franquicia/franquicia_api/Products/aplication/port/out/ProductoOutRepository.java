package com.franquicia.franquicia_api.Products.aplication.port.out;

import com.franquicia.franquicia_api.Products.domain.models.ProductoRequestDTO;
import com.franquicia.franquicia_api.Products.domain.models.ProductoStockResponseDTO;
import com.franquicia.franquicia_api.Products.insfrastructure.out.entity.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoOutRepository {
    Mono<Producto> save(ProductoRequestDTO producto);
    Flux<Producto> findBySucursalId(Long sucursalId);
    Mono<Producto> UpdateProducto(ProductoRequestDTO producto);
    Mono<Producto> UpdateProductoStock(ProductoRequestDTO producto);
    Mono<Void> deleteProductoFromSucursal(Long sucursalId, Long productoId);
    Flux<ProductoStockResponseDTO> findTopStockByFranquicia(Long franquiciaId);
}
