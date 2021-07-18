package org.aguzman.java.jdbc.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class ConexionBaseDatos {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "admin";
    private static BasicDataSource pool;

    private ConexionBaseDatos() {
    }

    /*
     Patron singleton para obtener una instancia de BasicDataSource.
     */
    private static BasicDataSource getBasicDataSource() throws SQLException {
        if (Objects.isNull(pool)) {
            pool = new BasicDataSource();
            pool.setUrl(URL);
            pool.setUsername(USERNAME);
            pool.setPassword(PASSWORD);

            //tamanio del pool inicial
            pool.setInitialSize(3);
            //minimo de conexiones inactivas, esperando a ser usadas
            pool.setMinIdle(3);
            //maximo de conexiones activas, esperando a ser usadas
            pool.setMaxIdle(8);
            //maximo de conexiones entre activas e inactivas
            pool.setMaxTotal(8);
            /*
             MaxWait es el momento en que su llamada desde la aplicacion, para obtener
             una conexión esperará en el grupo antes de lanzar una excepción cuando todas
             las conexiones estén ocupadas actualmente.
             */
            pool.setMaxWaitMillis(5000);
        }
        return pool;
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = getBasicDataSource().getConnection();
        if (connection.getAutoCommit()) connection.setAutoCommit(false);
        return connection;
    }

}
