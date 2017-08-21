package lab.dao.spring.jdbc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import lab.dao.CountryDao;
import lab.model.Country;

import lab.model.simple.SimpleCountry;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

public class SimpleCountryJdbcDao extends NamedParameterJdbcDaoSupport implements CountryDao {

	private static final String LOAD_COUNTRIES_SQL = "insert into country (name, code_name) values ('%s', '%s')";
	private static final String GET_ALL_COUNTRIES_SQL = "select id, name, code_name from country";
	private static final String GET_COUNTRIES_BY_NAME_SQL = "select id, name, code_name from country where name like :name";
	private static final String GET_COUNTRY_BY_NAME_SQL = "select id, name, code_name from country where name = '%s'";
	private static final String GET_COUNTRY_BY_CODE_NAME_SQL = "select id, name, code_name from country where code_name = '%s'";
    private static final String UPDATE_COUNTRY_NAME_SQL = "update country SET name='%s' where code_name='%s'";

    private static final RowMapper<Country> COUNTRY_ROW_MAPPER = (resultSet, rowNum) -> new SimpleCountry(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("code_name"));

	@Override
    public List<Country> getCountryList() {
		return Optional.ofNullable(getJdbcTemplate())
                .map(t -> t.query(GET_ALL_COUNTRIES_SQL, COUNTRY_ROW_MAPPER))
                .orElseGet(Collections::emptyList);
	}

	@Override
    public List<Country> getCountryListStartWith(String name) {
	    return Optional.ofNullable(getNamedParameterJdbcTemplate())
                .map(t -> t.query(GET_COUNTRIES_BY_NAME_SQL,
                        new MapSqlParameterSource("name", name + "%"), COUNTRY_ROW_MAPPER))
                .orElseGet(Collections::emptyList);
	}

	@Override
    public void updateCountryName(String codeName, String newCountryName) {
	    Optional.ofNullable(getJdbcTemplate())
                .ifPresent(t -> t.update(String.format(UPDATE_COUNTRY_NAME_SQL, newCountryName, codeName)));
	}

	@Override
    public void loadCountries() {
        Arrays.stream(COUNTRY_INIT_DATA).forEach(countryData ->
                Optional.ofNullable(getJdbcTemplate())
                        .ifPresent(t -> t.execute(String.format(LOAD_COUNTRIES_SQL, countryData[0], countryData[1]))));
	}

	@Override
    public Country getCountryByCodeName(String codeName) {
        return Optional.ofNullable(getJdbcTemplate())
                .map(t -> t.query(String.format(GET_COUNTRY_BY_CODE_NAME_SQL, codeName), COUNTRY_ROW_MAPPER))
                .map(countries -> countries.get(0))
                .orElse(null);
	}

	@Override
    public Country getCountryByName(String name) {
        return Optional.ofNullable(getJdbcTemplate())
                .map(t -> t.query(String.format(GET_COUNTRY_BY_NAME_SQL, name), COUNTRY_ROW_MAPPER))
                .filter(countries -> !countries.isEmpty())
                .map(countries -> countries.get(0))
                .orElse(null);
	}
}
