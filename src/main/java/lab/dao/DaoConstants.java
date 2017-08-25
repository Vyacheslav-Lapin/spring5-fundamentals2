package lab.dao;

public interface DaoConstants {
    String CREATE_COUNTRY_TABLE_SQL =
            "CREATE TABLE country(" +
                    "id IDENTITY," +
                    "name VARCHAR (255)," +
                    "code_name VARCHAR (255))";

    String DROP_COUNTRY_TABLE_SQL =
            "DROP TABLE country";
}
