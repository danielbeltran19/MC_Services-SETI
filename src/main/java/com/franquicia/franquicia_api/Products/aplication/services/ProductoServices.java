package com.franquicia.franquicia_api.Products.aplication.services;

import com.franquicia.franquicia_api.Products.aplication.port.in.ProductoInRepository;
import com.franquicia.franquicia_api.Products.aplication.port.out.ProductoOutRepository;
import com.franquicia.franquicia_api.Products.domain.models.ProductoRequestDTO;
import com.franquicia.franquicia_api.Products.domain.models.ProductoStockResponseDTO;
import com.franquicia.franquicia_api.Products.insfrastructure.out.entity.Producto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServices implements ProductoInRepository {

    private final ProductoOutRepository productoOutRepository;

    public ProductoServices(ProductoOutRepository productoOutRepository) {
        this.productoOutRepository = productoOutRepository;
    }

    @Override
    public Mono<Producto> saveProducto(ProductoRequestDTO request) {
        if (request.getNombre() == null) {
            return Mono.error(new IllegalArgumentException("El nombre no puede ser null"));
        }
        if (request.getStock() == null || request.getStock() < 0) {
            return Mono.error(new IllegalArgumentException("El stock no puede ser null o negativo"));
        }
        return productoOutRepository.save(request);
    }

    @Override
    public Mono<Producto> UpdateProducto(ProductoRequestDTO producto) {
        if (producto.getId() == null) {
            return Mono.error(new IllegalArgumentException("El id no puede ser null"));
        }
        return productoOutRepository.UpdateProducto(producto);
    }

    @Override
    public Mono<Producto> UpdateProductoStock(ProductoRequestDTO producto) {
        if (producto.getId() == null) {
            return Mono.error(new IllegalArgumentException("El id no puede ser null"));
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            return Mono.error(new IllegalArgumentException("El stock no puede ser null o negativo"));
        }
        return productoOutRepository.UpdateProductoStock(producto);
    }

    @Override
    public Mono<Void> deleteProductoFromSucursal(Long sucursalId, Long productoId) {
        if (sucursalId == null || productoId == null) {
            return Mono.error(new IllegalArgumentException("El id de la sucursal o del producto no pueden ser null"));
        }
        return productoOutRepository.deleteProductoFromSucursal(sucursalId, productoId);
    }

    @Override
    public Flux<ProductoStockResponseDTO> findTopStockByFranquicia(Long franquiciaId) {
        return productoOutRepository.findTopStockByFranquicia(franquiciaId);
    }
}
