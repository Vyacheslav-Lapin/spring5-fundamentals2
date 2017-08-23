package practice;

import io.vavr.CheckedConsumer;
import io.vavr.CheckedFunction1;
import lombok.SneakyThrows;
import lombok.val;

import java.sql.*;
import java.util.Properties;
import java.util.function.Supplier;

public interface ConnectionPool extends Supplier<Connection>, AutoCloseable {
    String DB_DRIVER = "driver";
    String DB_URL = "url";
    String DB_POLL_SIZE = "poolSize";

    @SneakyThrows
    static ConnectionPool byProperties(String propertiesFilePath) {
        val properties = new Properties();
        properties.load(Properties.class.getResourceAsStream(propertiesFilePath));

        Class.forName((String) properties.remove(DB_DRIVER));
        val url = (String) properties.remove(DB_URL);
        val poolSize = Integer.parseInt((String) properties.remove(DB_POLL_SIZE));

        return new ArrayBlockingQueueConnectionPool(poolSize,
                () -> getConnection(url, properties));
    }

    @Private
    @SneakyThrows
    static Connection getConnection(String url, Properties properties) {
        assert properties.containsKey("user");
        assert properties.containsKey("password");
        assert properties.size() == 2;
        return DriverManager.getConnection(url, properties);
    }

    @SneakyThrows
    default <T> T mapConnection(CheckedFunction1<Connection, T> connectionMapper) {
        try (val connection = get()) {
            return connectionMapper.apply(connection);
        }
    }

    @SneakyThrows
    default void withConnection(CheckedConsumer<Connection> connectionConsumer) {
        try (val connection = get()) {
            connectionConsumer.accept(connection);
        }
    }

    @SneakyThrows
    default <T> T mapStatement(CheckedFunction1<Statement, T> statementMapper) {
        return mapConnection(connection -> {
            try (Statement statement = connection.createStatement()) {
                return statementMapper.apply(statement);
            }
        });
    }

    @SneakyThrows
    default void withStatement(CheckedConsumer<Statement> statementConsumer) {
        withConnection(connection -> {
            try (Statement statement = connection.createStatement()) {
                statementConsumer.accept(statement);
            }
        });
    }

    // TODO: 18/08/2017 execute

    @SneakyThrows
    default <T> T mapPreparedStatement(String preparedSql,
                                       CheckedFunction1<PreparedStatement, T> preparedStatementMapper,
                                       Object... params) {
        return mapConnection(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(preparedSql)) {
                for (int i = 0; i < params.length; ) {
                    Object param = params[i++];
                    preparedStatement.setObject(i, param);
                }
                return preparedStatementMapper.apply(preparedStatement);
            }
        });
    }

    @SneakyThrows
    default void withPreparedStatement(String preparedSql,
                                       CheckedConsumer<PreparedStatement> preparedStatementConsumer,
                                       Object... params) {
        withConnection(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(preparedSql)) {
                for (int i = 0; i < params.length; ) {
                    Object param = params[i++];
                    preparedStatement.setObject(i, param);
                }
                preparedStatementConsumer.accept(preparedStatement);
            }
        });
    }

    // TODO: 18/08/2017 mapCallableStatement

    // TODO: 18/08/2017 withCallableStatement

    @SneakyThrows
    default <T> T mapResultSet(String sql,
                               CheckedFunction1<ResultSet, T> resultSetMapper) {
        return mapStatement(statement -> {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                return resultSetMapper.apply(resultSet);
            }
        });
    }

    @SneakyThrows
    default void withResultSet(String sql,
                               CheckedConsumer<ResultSet> resultSetConsumer) {
        withStatement(statement -> {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                resultSetConsumer.accept(resultSet);
            }
        });
    }

    // TODO: 18/08/2017 mapPreparedResultSet

    // TODO: 18/08/2017 withPreparedResultSet

    // TODO: 18/08/2017 mapCollableResultSet

    // TODO: 18/08/2017 withCollableResultSet

}
