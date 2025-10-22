package com.franquicia.franquicia_api.Branches.domain.models;

public class SucursalRequestDTO {
    private Long Id;
    private String nombre;
    private Long franquiciaId;

    public SucursalRequestDTO() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getFranquiciaId() {
        return franquiciaId;
    }

    public void setFranquiciaId(Long franquiciaId) {
        this.franquiciaId = franquiciaId;
    }
}
