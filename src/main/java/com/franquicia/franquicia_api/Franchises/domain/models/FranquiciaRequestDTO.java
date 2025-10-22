package com.franquicia.franquicia_api.Franchises.domain.models;

public class FranquiciaRequestDTO {
    private Long Id;
    private String nombre;

    public FranquiciaRequestDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
