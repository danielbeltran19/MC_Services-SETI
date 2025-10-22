package com.franquicia.franquicia_api.Products.aplication.port.in;

import com.franquicia.franquicia_api.Products.domain.models.ProductoRequestDTO;
import com.franquicia.franquicia_api.Products.domain.models.ProductoStockResponseDTO;
import com.franquicia.franquicia_api.Products.insfrastructure.out.entity.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoInRepository {
    Mono<Producto> saveProducto(ProductoRequestDTO producto);
    Mono<Producto> UpdateProducto(ProductoRequestDTO producto);
    Mono<Producto> UpdateProductoStock(ProductoRequestDTO producto);
    Mono<Void> deleteProductoFromSucursal(Long sucursalId, Long productoId);
    Flux<ProductoStockResponseDTO> findTopStockByFranquicia(Long franquiciaId);
}
