package org.aguzman.java.jdbc.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Producto {

    private Long id;
    private String nombre;
    private BigDecimal precio;
    private LocalDateTime fechaRegistro;
    private Categoria categoria;

    public Producto() {
    }

    public Producto(Long id, String nombre, BigDecimal precio, LocalDateTime fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.fechaRegistro = fechaRegistro;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return this.precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public LocalDateTime getFechaRegistro() {
        return this.fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Id: " + this.id + ", Nombre: " + this.nombre + ", Precio: " + this.precio +
                ", FechaRegistro: " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH::mm:ss").format(this.fechaRegistro) +
                ", " + this.categoria;
    }

}
