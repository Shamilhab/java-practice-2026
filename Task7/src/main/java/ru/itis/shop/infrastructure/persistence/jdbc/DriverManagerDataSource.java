package ru.itis.shop.infrastructure.persistence.jdbc;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;

public class DriverManagerDataSource implements DataSource {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    private Connection connection = null;

    public DriverManagerDataSource(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            makeConnection();
        }

        return connection;
    }

    private void makeConnection() {
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new RuntimeException("Not implemented");

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new RuntimeException("Not implemented");

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public ConnectionBuilder createConnectionBuilder() throws SQLException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public ShardingKeyBuilder createShardingKeyBuilder() throws SQLException {
        throw new RuntimeException("Not implemented");
    }
}