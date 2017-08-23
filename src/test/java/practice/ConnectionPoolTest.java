package practice;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConnectionPoolTest {

    public static final String PROPERTIES_FILE_PATH = "/jdbc.properties";

    static ConnectionPool connectionPool =
            ConnectionPool.byProperties(PROPERTIES_FILE_PATH);

    @BeforeAll
    @SneakyThrows
    static void setUp() {
        connectionPool.withStatement(statement ->
                statement.executeUpdate(
                        "CREATE TABLE country (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), code_name VARCHAR(255))"
                ));
    }

    @BeforeEach
    @SneakyThrows
    void initCountry() {
        connectionPool.withStatement(statement ->
                statement.executeUpdate(
                    "INSERT INTO country (name, code_name) VALUES ('Russia', 'Ru')"));
    }

    @Test
    @SneakyThrows
    void getCountry() {
        connectionPool.withStatement(statement -> {
            try (ResultSet rs = statement.executeQuery("SELECT * FROM country WHERE code_name='Ru'")) {
                assertTrue(rs.next());
                assertThat(rs.getInt("id"), is(1));
                assertThat(rs.getString("name"), is("Russia"));
                assertThat(rs.getString("code_name"), is("Ru"));
            }
        });
    }
}