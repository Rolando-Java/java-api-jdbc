package org.aguzman.java.jdbc;

import org.aguzman.java.jdbc.modelo.Categoria;
import org.aguzman.java.jdbc.modelo.Producto;
import org.aguzman.java.jdbc.service.CatalogoServicio;
import org.aguzman.java.jdbc.service.Servicio;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EjemploJdbc {

    public static void main(String... args) {
        try {
            Servicio servicio = new CatalogoServicio();

            System.out.println("Insertar producto con su categoria");
            Categoria categoria = new Categoria();
            categoria.setNombre("Iluminacion");

            Producto producto = new Producto();
            producto.setNombre("Lampara");
            producto.setPrecio(BigDecimal.valueOf(30.45));
            producto.setFechaRegistro(LocalDateTime.now());

            servicio.guardarProductoConCategoria(producto, categoria);

            System.out.println("====== listar categorias ======");
            servicio.listarCategorias().forEach(System.out::println);

            System.out.println("====== listar productos ======");
            servicio.listarProductos().forEach(System.out::println);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
