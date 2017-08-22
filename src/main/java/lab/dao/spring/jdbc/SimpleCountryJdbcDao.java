package lab.dao.spring.jdbc;

import lab.dao.CountryRowMapper;
import lab.model.Country;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

public class SimpleCountryJdbcDao extends JdbcDaoSupport implements lab.dao.CountryDao {

    private static final String GET_ALL_COUNTRIES_SQL = "SELECT * FROM country";
    private static final String GET_COUNTRIES_BY_NAME_SQL = "SELECT * FROM country WHERE name LIKE :name";
    private static final String GET_COUNTRY_BY_NAME_SQL = "select * from country where name = '";
    private static final String GET_COUNTRY_BY_CODE_NAME_SQL = "select * from country where code_name = '";

    private static final String UPDATE_COUNTRY_NAME_SQL_1 = "update country SET name=";
    private static final String UPDATE_COUNTRY_NAME_SQL_2 = " where code_name=";

    private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();

    @Override
    public List<Country> getCountryList() {
        JdbcTemplate template = createJdbcTemplate(getDataSource());
        return template.query(GET_ALL_COUNTRIES_SQL, COUNTRY_ROW_MAPPER);
    }

    @Override
    public List<Country> getCountryListStartWith(String name) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getDataSource());
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(
                "name", name + "%");
        return namedParameterJdbcTemplate.query(GET_COUNTRIES_BY_NAME_SQL,
                sqlParameterSource, COUNTRY_ROW_MAPPER);
    }

    @Override
    public void updateCountryName(String codeName, String newCountryName) {
        JdbcTemplate jdbcTemplate = createJdbcTemplate(getDataSource());
        String sql = UPDATE_COUNTRY_NAME_SQL_1 + "?" + UPDATE_COUNTRY_NAME_SQL_2 + "?";
        jdbcTemplate.update(sql, newCountryName, codeName);
    }

    @Override
    public Country getCountryByCodeName(String codeName) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        String sql = GET_COUNTRY_BY_CODE_NAME_SQL + codeName + "'";
//		System.out.println(sql);

        return jdbcTemplate.query(sql, COUNTRY_ROW_MAPPER).get(0);
    }

    @Override
    public Country getCountryByName(String name) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        List<Country> countryList = jdbcTemplate.query(GET_COUNTRY_BY_NAME_SQL
                + name + "'", COUNTRY_ROW_MAPPER);
        if (countryList.isEmpty()) {
            return null;
        }
        return countryList.get(0);
    }
}
