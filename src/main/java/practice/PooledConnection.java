package practice;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

@AllArgsConstructor
public class PooledConnection implements Connection {

    @Delegate(excludes = Closeable.class)
    private Connection connection;
    private Consumer<Connection> freeConnection;

    public void reallyClose() throws SQLException {
        connection.close();
    }

    @Override
    public void close() throws SQLException {
        if (connection.isClosed())
            throw new SQLException("Attempting to close closed connection.");

        if (connection.isReadOnly())
            connection.setReadOnly(false);

        freeConnection.accept(connection);
    }
}
