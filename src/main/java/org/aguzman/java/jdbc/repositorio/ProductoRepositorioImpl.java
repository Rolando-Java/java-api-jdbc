package org.aguzman.java.jdbc.repositorio;

import org.aguzman.java.jdbc.modelo.Categoria;
import org.aguzman.java.jdbc.modelo.Producto;

import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProductoRepositorioImpl implements Repositorio<Producto> {

    private static final String SQL_SELECT_PRODUCTOS = "SELECT p.*, c.nombre as categoria FROM java_curso.productos p " +
            "INNER JOIN java_curso.categorias c ON c.id = p.categoria_id";
    private static final String SQL_SELECT_PRODUCTO_BY_ID = "SELECT p.*, c.nombre as categoria FROM java_curso.productos p " +
            "INNER JOIN java_curso.categorias c ON c.id = p.categoria_id WHERE p.id=?";
    private static final String SQL_INSERT_PRODUCTO = "INSERT INTO java_curso.productos(nombre, precio, categoria_id, fecha_registro) VALUES(?, ?, ?, ?)";
    private static final String SQL_UPDATE_PRODUCTO_BY_ID = "UPDATE java_curso.productos SET nombre=?, precio=?, categoria_id=? WHERE id=?";
    private static final String SQL_DELETE_PRODUCTO_BY_ID = "DELETE FROM java_curso.productos WHERE id=?";

    private Connection connection;

    public ProductoRepositorioImpl() {

    }
    
    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try (
                Statement statement = this.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_PRODUCTOS)
        ) {
            while (resultSet.next()) productos.add(this.crearProducto(resultSet));
        }
        return productos;
    }

    @Override
    public Optional<Producto> porId(Long id) throws SQLException {
        Producto producto = null;
        try (
                PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_SELECT_PRODUCTO_BY_ID)
        ) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) producto = this.crearProducto(resultSet);
            }
        }

        return Optional.ofNullable(producto);
    }

    @Override
    public Producto guardar(Producto producto) throws SQLException {
        try (
                PreparedStatement preparedStatement = this.connection
                        .prepareStatement((!Objects.isNull(producto.getId())) ? SQL_UPDATE_PRODUCTO_BY_ID
                                : SQL_INSERT_PRODUCTO, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setBigDecimal(2, producto.getPrecio());
            preparedStatement.setLong(3, producto.getCategoria().getId());

            if (!Objects.isNull(producto.getId())) preparedStatement.setLong(4, producto.getId());
            else preparedStatement.setTimestamp(4, Timestamp.valueOf(producto.getFechaRegistro()));

            preparedStatement.executeUpdate();

            if (Objects.isNull(producto.getId())) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) producto.setId(resultSet.getLong("id"));
                }
            }
        }

        return producto;
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try (
                PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_DELETE_PRODUCTO_BY_ID)
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

    private Producto crearProducto(ResultSet resultSet) throws SQLException {
        Producto producto = new Producto();
        producto.setId(resultSet.getLong("id"));
        producto.setNombre(resultSet.getString("nombre"));
        producto.setPrecio(resultSet.getBigDecimal("precio").setScale(2, RoundingMode.HALF_UP));
        producto.setFechaRegistro(resultSet.getTimestamp("fecha_registro").toLocalDateTime());

        Categoria categoria = new Categoria();
        categoria.setId(resultSet.getLong("categoria_id"));
        categoria.setNombre(resultSet.getString("categoria"));
        producto.setCategoria(categoria);

        return producto;
    }
}
