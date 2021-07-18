package org.aguzman.java.jdbc.repositorio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Repositorio<T> {

    void setConnection(Connection connection);

    List<T> listar() throws SQLException;

    Optional<T> porId(Long id) throws SQLException;

    T guardar(T elemento) throws SQLException;

    void eliminar(Long id) throws SQLException;

}
