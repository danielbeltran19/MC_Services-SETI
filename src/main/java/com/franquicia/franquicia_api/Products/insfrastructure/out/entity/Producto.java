package com.franquicia.franquicia_api.Products.insfrastructure.out.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
@Data
@Builder
@AllArgsConstructor   // ✅ Constructor con todos los campos
@NoArgsConstructor
@Table("productos")
public class Producto {
    @Id
    private Long id;

    private String nombre;

    private Integer stock;

    @Column("sucursal_id")
    private Long sucursalId;

}
