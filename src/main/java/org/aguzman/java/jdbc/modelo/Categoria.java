package org.aguzman.java.jdbc.modelo;

public class Categoria {

    private Long id;
    private String nombre;

    public Categoria() {

    }

    public Categoria(Long id) {
        this.id = id;
    }

    public Categoria(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return "Categoria: " + this.nombre;
    }

}
