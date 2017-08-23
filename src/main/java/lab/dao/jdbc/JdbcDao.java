package lab.dao.jdbc;

import lombok.SneakyThrows;
import lombok.val;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public interface JdbcDao extends Supplier<Connection> {

    @SneakyThrows
    default <T> T mapConnection(Function<Connection, T> connectionFunction) {
        try (Connection connection = get()) {
            return connectionFunction.apply(connection);
        }
    }

    @SneakyThrows
    default <T> T mapPreparedStatement(
            String sql,
            Function<PreparedStatement, T> preparedStatementFunction,
            Object... params) {
        return mapConnection(connection -> {
            try (val preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < params.length; i++)
                    preparedStatement.setObject(i, params[i]);
                return preparedStatementFunction.apply(preparedStatement);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @SneakyThrows
    default <T> T mapPreparedStatementWithKey(
            String sql,
            Function<PreparedStatement, T> preparedStatementFunction,
            Consumer<Number> keyConsumer,
            Object... params) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        return mapConnection(connection -> {
            try (val preparedStatement = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++)
                    preparedStatement.setObject(i, params[i]);
                return preparedStatementFunction.apply(preparedStatement);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                keyConsumer.accept(keyHolder.getKey());
            }
        });
    }

    @SneakyThrows
    default <T> T mapResultSet() {
        return null;
    }
}
