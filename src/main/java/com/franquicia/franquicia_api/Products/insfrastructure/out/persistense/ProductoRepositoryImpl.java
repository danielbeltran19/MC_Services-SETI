package com.franquicia.franquicia_api.Products.insfrastructure.out.persistense;

import com.franquicia.franquicia_api.Branches.insfrastructure.out.repository.SucursalJpaRepository;
import com.franquicia.franquicia_api.Franchises.insfrastructure.out.persistense.FranquiciaRepositoryImpl;
import com.franquicia.franquicia_api.Products.aplication.port.out.ProductoOutRepository;
import com.franquicia.franquicia_api.Products.domain.models.ProductoRequestDTO;
import com.franquicia.franquicia_api.Products.domain.models.ProductoStockResponseDTO;
import com.franquicia.franquicia_api.Products.insfrastructure.out.entity.Producto;
import com.franquicia.franquicia_api.Products.insfrastructure.out.mapper.ProductoMapper;
import com.franquicia.franquicia_api.Products.insfrastructure.out.repository.ProductoJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ProductoRepositoryImpl implements ProductoOutRepository {

    private final ProductoJpaRepository productoJpaRepository;
    private final ProductoMapper productoMapper;
    private final SucursalJpaRepository sucursalJpaRepository;

    private static final Logger log = LoggerFactory.getLogger(ProductoRepositoryImpl.class);


    public ProductoRepositoryImpl(ProductoJpaRepository productoJpaRepository, ProductoMapper productoMapper,SucursalJpaRepository sucursalJpaRepository) {
        this.productoJpaRepository = productoJpaRepository;
        this.productoMapper = productoMapper;
        this.sucursalJpaRepository = sucursalJpaRepository;
    }

    @Override
    public Mono<Producto> save(ProductoRequestDTO productoRequestDTO) {
        Producto productoEntity = productoMapper.toEntity(productoRequestDTO);

        return sucursalJpaRepository.findById(productoEntity.getSucursalId())
                .switchIfEmpty(Mono.error(new RuntimeException("La sucursal con ID " + productoEntity.getSucursalId() + " no existe")))
                .flatMap(sucursal -> productoJpaRepository.save(productoEntity)
                        .doOnNext(p -> log.info("Producto guardado: {}", p.getId()))
                        .doOnError(e -> log.error("Error al guardar producto: {}", e.getMessage(), e))
                );
    }

    @Override
    public Flux<Producto> findBySucursalId(Long sucursalId) {
        return productoJpaRepository.findBySucursalId(sucursalId)
                .doOnError(e -> log.error("Error al buscar productos: {}", e.getMessage(), e));
    }

    @Override
    public Mono<Producto> UpdateProducto(ProductoRequestDTO producto) {
        return productoJpaRepository.findById(producto.getId())
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "Producto con ID " + producto.getId() + " no encontrada")))
                .flatMap(existingSucursal -> {
                    existingSucursal.setNombre(producto.getNombre());
                    return productoJpaRepository.save(existingSucursal);
                })
                .doOnNext(s -> log.info("Producto actualizada: {} -> {}", s.getId(), s.getNombre()))
                .doOnError(e -> log.error("Error al actualizar sucursal: {}", e.getMessage(), e));
    }

    @Override
    public Mono<Producto> UpdateProductoStock(ProductoRequestDTO producto) {
        return productoJpaRepository.findById(producto.getId())
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "Producto con ID " + producto.getId() + " no encontrada")))
                .flatMap(existingSucursal -> {
                    existingSucursal.setStock(producto.getStock());
                    return productoJpaRepository.save(existingSucursal);
                })
                .doOnNext(s -> log.info("Stock de producto actualizado: {} -> {}", s.getId(), s.getNombre()))
                .doOnError(e -> log.error("Error al actualizar producto: {}", e.getMessage(), e));
    }

    @Override
    public Mono<Void> deleteProductoFromSucursal(Long sucursalId, Long productoId) {
        return productoJpaRepository.findBySucursalIdAndId(sucursalId, productoId)
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "El producto no existe en la sucursal " + sucursalId)))
                .flatMap(producto -> productoJpaRepository.delete(producto)
                        .doOnSuccess(v -> log.info("Producto {} eliminado de sucursal {}", productoId, sucursalId))
                )
                .doOnError(e -> log.error("Error al eliminar producto de sucursal: {}", e.getMessage()));
    }

    @Override
    public Flux<ProductoStockResponseDTO> findTopStockByFranquicia(Long franquiciaId) {
        return productoJpaRepository.findTopStockByFranquiciaId(franquiciaId)
                .flatMap(producto ->
                        sucursalJpaRepository.findById(producto.getSucursalId())
                                .map(sucursal -> productoMapper.toResponseDTO(producto, sucursal))
                )
                .doOnNext(p -> log.info("Producto con más stock: {} - Sucursal: {}", p.getProductoNombre(), p.getSucursalNombre()))
                .doOnError(e -> log.error("Error al obtener productos con más stock: {}", e.getMessage()));
    }

}
