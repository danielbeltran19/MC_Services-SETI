package com.franquicia.franquicia_api.Products.insfrastructure.in.rest;

import com.franquicia.franquicia_api.Franchises.domain.models.FranquiciaRequestDTO;
import com.franquicia.franquicia_api.Products.aplication.port.in.ProductoInRepository;
import com.franquicia.franquicia_api.Products.domain.models.ProductoRequestDTO;
import com.franquicia.franquicia_api.Products.domain.models.ProductoStockResponseDTO;
import com.franquicia.franquicia_api.Products.insfrastructure.out.entity.Producto;
import com.franquicia.franquicia_api.shared.util.ResponseData;
import com.franquicia.franquicia_api.shared.util.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("${api.base.path}/productos")
public class ProductoController {

    private final ProductoInRepository productoService;

    public ProductoController(ProductoInRepository productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<ResponseMessage>> createProducto(@RequestBody ProductoRequestDTO request) {
        return productoService.saveProducto(request)
                .map(producto -> {
                    ResponseMessage response = new ResponseMessage(
                            HttpStatus.CREATED.name(),
                            HttpStatus.CREATED.value(),
                            "Producto creado correctamente con ID: " + producto.getId()
                    );
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                })
                .onErrorResume(error -> {
                    ResponseMessage response = new ResponseMessage(
                            HttpStatus.BAD_REQUEST.name(),
                            HttpStatus.BAD_REQUEST.value(),
                            "Error al crear el producto: " + error.getMessage()
                    );
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
                });
    }

    @PutMapping("/update/{productoId}")
    public Mono<ResponseEntity<ResponseMessage>> updateProducto(
            @PathVariable Long productoId,
            @RequestBody ProductoRequestDTO request) {
        request.setId(productoId);
        return productoService.UpdateProducto(request)
                .map(updated -> ResponseEntity.ok(
                        new ResponseMessage(
                                HttpStatus.OK.name(),
                                HttpStatus.OK.value(),
                                "Producto actualizado correctamente con ID: " + updated.getId()
                        )))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseMessage(
                                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Error al actualizar el producto: " + error.getMessage()
                        ))));
    }

    @PutMapping("/update/{productoId}/stock")
    public Mono<ResponseEntity<ResponseMessage>> updateProductoStock(
            @PathVariable Long productoId,
            @RequestBody ProductoRequestDTO request) {
        request.setId(productoId);
        return productoService.UpdateProductoStock(request)
                .map(updated -> ResponseEntity.ok(
                        new ResponseMessage(
                                HttpStatus.OK.name(),
                                HttpStatus.OK.value(),
                                "Stock actualizado correctamente con ID: " + updated.getId()
                        )))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseMessage(
                                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Error al actualizar stock: " + error.getMessage()
                        ))));
    }

    @DeleteMapping("/sucursal/{sucursalId}/producto/{productoId}")
    public Mono<ResponseEntity<ResponseMessage>> deleteProductoDeSucursal(
            @PathVariable Long sucursalId,
            @PathVariable Long productoId) {
        return productoService.deleteProductoFromSucursal(sucursalId, productoId)
                .then(Mono.just(ResponseEntity.ok(
                        new ResponseMessage(HttpStatus.OK.name(), HttpStatus.OK.value(),
                                "Producto eliminado correctamente de la sucursal")
                )))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseMessage(
                                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Error al eliminar producto de sucursal: " + error.getMessage()
                        ))));
    }

    @GetMapping("/top-stock/franquicia/{franquiciaId}")
    public Mono<ResponseEntity<ResponseData<List<ProductoStockResponseDTO>>>> getTopStockByFranquicia(
            @PathVariable Long franquiciaId) {

        return productoService.findTopStockByFranquicia(franquiciaId)
                .collectList()
                .map(lista -> ResponseEntity.ok(
                        new ResponseData<>(
                                HttpStatus.OK.name(),
                                HttpStatus.OK.value(),
                                lista
                        )))
                .onErrorResume(error -> Mono.just(
                        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ResponseData<>(
                                        HttpStatus.INTERNAL_SERVER_ERROR.name(),
                                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                        Collections.emptyList()
                                ))
                ));
    }
}

