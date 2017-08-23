package lab.dao.jdbc;

import io.vavr.CheckedConsumer;
import io.vavr.CheckedFunction1;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.function.LongConsumer;
import java.util.function.Supplier;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@FunctionalInterface
public interface JdbcDao extends Supplier<Connection> {

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

    @SneakyThrows
    default <T> T mapPreparedStatement(
            String sql,
            CheckedFunction1<PreparedStatement, T> preparedStatementMapper,
            Object... params) {
        return mapConnection(connection -> {
            try (val preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < params.length; i++)
                    preparedStatement.setObject(i, params[i]);
                return preparedStatementMapper.apply(preparedStatement);
            }
        });
    }

    @SneakyThrows
    default void withPreparedStatement(
            String sql,
            CheckedConsumer<PreparedStatement> preparedStatementMapper,
            Object... params) {
        withConnection(connection -> {
            try (val preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 1; i <= params.length; i++)
                    preparedStatement.setObject(i, params[i - 1]);
                preparedStatementMapper.accept(preparedStatement);
            }
        });
    }

    @SneakyThrows
    default <T> T mapPreparedStatementWithKey(
            String sql,
            CheckedFunction1<PreparedStatement, T> preparedStatementFunction,
            LongConsumer keyConsumer,
            Object... params) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        return mapConnection(connection -> {
            try (val preparedStatement = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
                for (int i = 1; i < params.length; i++)
                    preparedStatement.setObject(i, params[i - 1]);
                return preparedStatementFunction.apply(preparedStatement);
            } finally {
                keyConsumer.accept(keyHolder.getKey().longValue());
            }
        });
    }

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
}
