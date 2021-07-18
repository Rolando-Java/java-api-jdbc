package org.aguzman.java.jdbc.service;

import org.aguzman.java.jdbc.modelo.Categoria;
import org.aguzman.java.jdbc.modelo.Producto;
import org.aguzman.java.jdbc.repositorio.CategoriaRepositorioImpl;
import org.aguzman.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.aguzman.java.jdbc.repositorio.Repositorio;
import org.aguzman.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CatalogoServicio implements Servicio {

    private Repositorio<Producto> repoProducto;
    private Repositorio<Categoria> repoCategoria;

    public CatalogoServicio() {
        this.repoProducto = new ProductoRepositorioImpl();
        this.repoCategoria = new CategoriaRepositorioImpl();
    }

    @Override
    public List<Producto> listarProductos() throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()) {
            this.repoProducto.setConnection(connection);
            return this.repoProducto.listar();
        }
    }

    @Override
    public Producto porIdProducto(Long id) throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()) {
            this.repoProducto.setConnection(connection);
            return this.repoProducto.porId(id).orElseGet(Producto::new);
        }
    }

    @Override
    public Producto guardarProducto(Producto producto) throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()) {
            this.repoProducto.setConnection(connection);
            try {
                this.repoProducto.guardar(producto);
                connection.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                connection.rollback();
            }
            return producto;
        }
    }

    @Override
    public void eliminarProducto(Long id) throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()) {
            this.repoProducto.setConnection(connection);
            try {
                this.repoProducto.eliminar(id);
                connection.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                connection.rollback();
            }
        }
    }

    @Override
    public List<Categoria> listarCategorias() throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()) {
            this.repoCategoria.setConnection(connection);
            return this.repoCategoria.listar();
        }
    }

    @Override
    public Categoria porIdCategoria(Long id) throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()) {
            this.repoCategoria.setConnection(connection);
            return this.repoCategoria.porId(id).orElseGet(Categoria::new);
        }
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()) {
            this.repoCategoria.setConnection(connection);
            try {
                this.repoCategoria.guardar(categoria);
                connection.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                connection.rollback();
            }
            return categoria;
        }
    }

    @Override
    public void eliminarCategoria(Long id) throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()) {
            this.repoCategoria.setConnection(connection);
            try {
                this.repoCategoria.eliminar(id);
                connection.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                connection.rollback();
            }
        }
    }

    @Override
    public void guardarProductoConCategoria(Producto producto, Categoria categoria) throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()) {
            this.repoProducto.setConnection(connection);
            this.repoCategoria.setConnection(connection);
            try {
                this.repoCategoria.guardar(categoria);
                producto.setCategoria(categoria);
                this.repoProducto.guardar(producto);
                connection.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                connection.rollback();
            }
        }
    }
}
