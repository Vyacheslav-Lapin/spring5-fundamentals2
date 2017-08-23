package practice;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ArrayBlockingQueueConnectionPool implements ConnectionPool {

    private BlockingQueue<Connection> connectionQueue;

    @SneakyThrows
    ArrayBlockingQueueConnectionPool(int poolSize, Supplier<Connection> connectionSupplier) {
        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        Consumer<Connection> offer = connectionQueue::offer;
        for (int i = 0; i < poolSize; i++)
            connectionQueue.add(
                    PooledConnection.from(connectionSupplier.get(), offer));
    }

    @Override
    @SneakyThrows
    public Connection get() {
        return connectionQueue.take();
    }

    @Override
    @SneakyThrows
    public void close() {
        Connection connection;
        while ((connection = connectionQueue.poll()) != null) {
            if (!connection.getAutoCommit())
                connection.commit();
            ((PooledConnection) connection).reallyClose();
        }
    }
}
