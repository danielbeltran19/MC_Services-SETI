package com.franquicia.franquicia_api.Products.service;

import com.franquicia.franquicia_api.Products.aplication.port.out.ProductoOutRepository;
import com.franquicia.franquicia_api.Products.aplication.services.ProductoServices;
import com.franquicia.franquicia_api.Products.domain.models.ProductoRequestDTO;
import com.franquicia.franquicia_api.Products.insfrastructure.out.entity.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductoServicesTest {
    @Mock
    private ProductoOutRepository productoOutRepository;

    @InjectMocks
    private ProductoServices productoServices;

    private ProductoRequestDTO request;
    private Producto producto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        request = new ProductoRequestDTO();
        request.setId(1L);
        request.setNombre("Pepsi");
        request.setStock(50);
        request.setSucursalId(2L);

        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Pepsi");
        producto.setStock(50);
        producto.setSucursalId(2L);
    }

    @Test
    void saveProducto_Success() {
        when(productoOutRepository.save(any())).thenReturn(Mono.just(producto));

        StepVerifier.create(productoServices.saveProducto(request))
                .expectNext(producto)
                .verifyComplete();

        verify(productoOutRepository, times(1)).save(any());
    }

    @Test
    void saveProducto_ErrorNombreNull() {
        request.setNombre(null);

        StepVerifier.create(productoServices.saveProducto(request))
                .expectErrorMatches(throwable ->
                        throwable instanceof IllegalArgumentException &&
                                throwable.getMessage().equals("El nombre no puede ser null"))
                .verify();
    }

    @Test
    void saveProducto_ErrorStockNegativo() {
        request.setStock(-10);

        StepVerifier.create(productoServices.saveProducto(request))
                .expectErrorMatches(throwable ->
                        throwable instanceof IllegalArgumentException &&
                                throwable.getMessage().equals("El stock no puede ser null o negativo"))
                .verify();
    }

    @Test
    void updateProducto_Success() {
        when(productoOutRepository.UpdateProducto(any())).thenReturn(Mono.just(producto));

        StepVerifier.create(productoServices.UpdateProducto(request))
                .expectNext(producto)
                .verifyComplete();

        verify(productoOutRepository, times(1)).UpdateProducto(any());
    }

    @Test
    void updateProductoStock_ErrorStockNegativo() {
        request.setStock(-10);

        StepVerifier.create(productoServices.UpdateProductoStock(request))
                .expectErrorMatches(throwable ->
                        throwable instanceof IllegalArgumentException &&
                                throwable.getMessage().equals("El stock no puede ser null o negativo"))
                .verify();
    }

    @Test
    void deleteProducto_Success() {
        when(productoOutRepository.deleteProductoFromSucursal(1L, 1L)).thenReturn(Mono.empty());

        StepVerifier.create(productoServices.deleteProductoFromSucursal(1L, 1L))
                .verifyComplete();

        verify(productoOutRepository, times(1)).deleteProductoFromSucursal(1L, 1L);
    }
}
