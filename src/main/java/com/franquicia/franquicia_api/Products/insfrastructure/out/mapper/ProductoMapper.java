package com.franquicia.franquicia_api.Products.insfrastructure.out.mapper;

import com.franquicia.franquicia_api.Branches.insfrastructure.out.entity.Sucursal;
import com.franquicia.franquicia_api.Products.domain.models.ProductoRequestDTO;
import com.franquicia.franquicia_api.Products.domain.models.ProductoStockResponseDTO;
import com.franquicia.franquicia_api.Products.insfrastructure.out.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {
    public static Producto toEntity(ProductoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Producto entity = new Producto();
        entity.setNombre(dto.getNombre());
        entity.setStock(dto.getStock());
        entity.setSucursalId(dto.getSucursalId());
        return entity;
    }

    public ProductoStockResponseDTO toResponseDTO(Producto producto, Sucursal sucursal) {
        if (producto == null || sucursal == null) {
            return null;
        }

        ProductoStockResponseDTO dto = new ProductoStockResponseDTO();
        dto.setProductoId(producto.getId());
        dto.setProductoNombre(producto.getNombre());
        dto.setStock(producto.getStock());
        dto.setSucursalId(sucursal.getId());
        dto.setSucursalNombre(sucursal.getNombre());
        return dto;
    }
}
