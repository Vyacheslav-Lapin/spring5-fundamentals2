package lab.dao.spring.jdbc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lab.model.Country;

import lab.model.simple.SimpleCountry;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class SimpleCountryJdbcDao extends JdbcDaoSupport implements lab.dao.CountryDao {
    private static final String LOAD_COUNTRIES_SQL = "INSERT INTO country (name, code_name) VALUES ('%s', '%s')";

    private static final String GET_ALL_COUNTRIES_SQL = "SELECT * FROM country";
    private static final String GET_COUNTRIES_BY_NAME_SQL = "SELECT * FROM country WHERE name LIKE :name";
    private static final String GET_COUNTRY_BY_NAME_SQL = "SELECT * FROM country WHERE name = '%s'";
    private static final String GET_COUNTRY_BY_CODE_NAME_SQL = "SELECT * FROM country WHERE code_name = '%s'";

    private static final String UPDATE_COUNTRY_NAME_SQL = "UPDATE country SET name = '%s' WHERE code_name = '%s'";

    private static final RowMapper<Country> COUNTRY_ROW_MAPPER =
            (row, rowNum) -> new SimpleCountry(row.getInt("id"), row.getString("name"), row.getString("code_name"));

    @Override
    public List<Country> getCountryList() {
        return Optional.ofNullable(getJdbcTemplate())
                       .map(template -> template.query(GET_ALL_COUNTRIES_SQL, COUNTRY_ROW_MAPPER))
                       .orElse(Collections.emptyList());
    }

    @Override
    public List<Country> getCountryListStartWith(final String name) {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());
        final SqlParameterSource parameterSource = new MapSqlParameterSource("name", name + "%");
        return template.query(GET_COUNTRIES_BY_NAME_SQL, parameterSource, COUNTRY_ROW_MAPPER);
    }

    @Override
    public void updateCountryName(final String codeName, final String newCountryName) {
        Optional.ofNullable(getJdbcTemplate())
                .ifPresent(
                        template -> template.execute(String.format(UPDATE_COUNTRY_NAME_SQL, newCountryName, codeName))
                );
    }

    @Override
    public void loadCountries() {
        Arrays.stream(COUNTRY_INIT_DATA)
              .map(countryData -> String.format(LOAD_COUNTRIES_SQL, countryData[0], countryData[1]))
              .forEach(sql -> Optional.ofNullable(getJdbcTemplate()).ifPresent(template -> template.execute(sql)));
    }

    @Override
    public Country getCountryByCodeName(final String codeName) {
        return Optional.ofNullable(getJdbcTemplate())
                       .map(template -> template.query(String.format(GET_COUNTRY_BY_CODE_NAME_SQL, codeName), COUNTRY_ROW_MAPPER).get(0))
                       .orElseThrow(RuntimeException::new);
    }

    @Override
    public Country getCountryByName(final String name) {
        return Optional.ofNullable(getJdbcTemplate())
                       .map(template -> template.query(String.format(GET_COUNTRY_BY_NAME_SQL, name), COUNTRY_ROW_MAPPER))
                       .map(countries -> countries.get(0)).orElse(null);
    }
}
