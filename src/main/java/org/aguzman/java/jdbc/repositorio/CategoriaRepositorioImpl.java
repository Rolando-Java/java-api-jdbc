package org.aguzman.java.jdbc.repositorio;

import org.aguzman.java.jdbc.modelo.Categoria;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.sql.Types.BIGINT;
import static java.sql.Types.REF_CURSOR;

public class CategoriaRepositorioImpl implements Repositorio<Categoria> {

    private static final String FUNC_SELECT_CATEGORIAS = "{ ? = call java_curso.func_select_categorias() }";
    private static final String FUNC_SELECT_CATEGORIA_BY_ID = "{ ? = call java_curso.func_select_categoria_by_id(?) }";
    private static final String FUNC_INSERT_CATEGORIA = "{ ? = call java_curso.func_insert_categoria(?) }";
    private static final String FUNC_UPDATE_CATEGORIA_BY_ID = "{ call java_curso.func_update_categoria_by_id(?, ?) }";
    private static final String FUNC_DELETE_CATEGORIA_BY_ID = "{ call java_curso.func_delete_categoria_by_id(?) }";

    private Connection connection;

    public CategoriaRepositorioImpl() {

    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Categoria> listar() throws SQLException {
        List<Categoria> categoriaList = new ArrayList<>();
        try (
                CallableStatement callableStatement = this.connection.prepareCall(FUNC_SELECT_CATEGORIAS)
        ) {
            callableStatement.registerOutParameter(1, REF_CURSOR);
            callableStatement.execute();

            try (ResultSet resultSet = callableStatement.getObject(1, ResultSet.class)) {
                while (resultSet.next()) categoriaList.add(this.crearCategoria(resultSet));
            }
        }

        return categoriaList;
    }

    @Override
    public Optional<Categoria> porId(Long id) throws SQLException {
        Categoria categoria = null;
        try (
                CallableStatement callableStatement = this.connection.prepareCall(FUNC_SELECT_CATEGORIA_BY_ID)
        ) {
            callableStatement.registerOutParameter(1, REF_CURSOR);
            callableStatement.setLong(2, id);
            callableStatement.execute();

            try (ResultSet resultSet = callableStatement.getObject(1, ResultSet.class)) {
                if (resultSet.next()) categoria = this.crearCategoria(resultSet);
            }
        }

        return Optional.ofNullable(categoria);
    }

    @Override
    public Categoria guardar(Categoria categoria) throws SQLException {
        try (
                CallableStatement callableStatement = this.connection
                        .prepareCall((!Objects.isNull(categoria.getId())) ? FUNC_UPDATE_CATEGORIA_BY_ID : FUNC_INSERT_CATEGORIA)
        ) {

            if (!Objects.isNull(categoria.getId())) {
                callableStatement.setString(1, categoria.getNombre());
                callableStatement.setLong(2, categoria.getId());
            } else {
                callableStatement.registerOutParameter(1, BIGINT);
                callableStatement.setString(2, categoria.getNombre());
            }

            callableStatement.execute();

            if (Objects.isNull(categoria.getId())) categoria.setId(callableStatement.getLong(1));
        }

        return categoria;
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try (
                CallableStatement callableStatement = this.connection.prepareCall(FUNC_DELETE_CATEGORIA_BY_ID)
        ) {
            callableStatement.setLong(1, id);
            callableStatement.execute();
        }
    }

    private Categoria crearCategoria(ResultSet resultSet) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(resultSet.getLong("id"));
        categoria.setNombre(resultSet.getString("nombre"));
        return categoria;
    }

}
