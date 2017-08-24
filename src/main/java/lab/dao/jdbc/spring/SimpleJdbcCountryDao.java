package lab.dao.jdbc.spring;

import io.vavr.CheckedConsumer;
import io.vavr.CheckedFunction1;
import io.vavr.control.Try;
import lab.dao.jdbc.JdbcCountryDao;
import lab.model.Country;
import lab.model.simple.SimpleCountry;
import lombok.SneakyThrows;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SimpleJdbcCountryDao extends NamedParameterJdbcDaoSupport implements JdbcCountryDao {

    private static final String LOAD_COUNTRIES_SQL = "INSERT INTO country (name, code_name) VALUES ('%s', '%s');";
    private static final String GET_ALL_COUNTRIES_SQL = "SELECT id, name, code_name FROM country";
    private static final String GET_COUNTRIES_BY_NAME_SQL = "SELECT id, name, code_name FROM country WHERE name LIKE :name";
    private static final String GET_COUNTRY_BY_NAME_SQL = "SELECT id, name, code_name FROM country WHERE name = '%s'";
    private static final String GET_COUNTRY_BY_CODE_NAME_SQL = "SELECT id, name, code_name FROM country WHERE code_name = '%s'";
    private static final String UPDATE_COUNTRY_NAME_SQL = "UPDATE country SET name='%s' WHERE code_name='%s'";
    private static final String INSERT_COUNTRY_SQL = "INSERT INTO country (name, code_name) VALUES (?, ?)";
    private static final String REMOVE_COUNTRY_SQL = "DELETE FROM country WHERE id = '%d'";

    private static final RowMapper<Country> COUNTRY_ROW_MAPPER = (resultSet, rowNum) ->
            new SimpleCountry(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("code_name"));

    private static final Supplier<RuntimeException> runtimeExceprion = () -> {
        throw new RuntimeException("DB has not initialized!");
    };

    private <T> T mapJdbcTemplate(CheckedFunction1<JdbcTemplate, T> jdbcTemplateMapper) {
        return Optional.ofNullable(getJdbcTemplate())
                .flatMap(t ->
                        Try.of(() -> jdbcTemplateMapper.apply(t)).toJavaOptional())
                .orElseThrow(runtimeExceprion);
    }

    private void withJdbcTemplate(CheckedConsumer<JdbcTemplate> jdbcTemplateConsumer) {
        Optional.ofNullable(getJdbcTemplate())
                .ifPresent(t ->
                        Try.run(() -> jdbcTemplateConsumer.accept(t)));
    }

    private <T> T mapNamedParameterJdbcTemplate(CheckedFunction1<NamedParameterJdbcTemplate, T> namedParameterJdbcTemplateMapper) {
        return Optional.ofNullable(getNamedParameterJdbcTemplate())
                .flatMap(t ->
                        Try.of(() -> namedParameterJdbcTemplateMapper.apply(t)).toJavaOptional())
                .orElseThrow(runtimeExceprion);
    }

    @SneakyThrows
    private void withNamedParameterJdbcTemplate(CheckedConsumer<NamedParameterJdbcTemplate> namedParameterJdbcTemplateConsumer) {
        Optional.ofNullable(getNamedParameterJdbcTemplate())
                .ifPresent(t ->
                        Try.run(() -> namedParameterJdbcTemplateConsumer.accept(t)));
    }

    @Override
    public void save(@NotNull Country country) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        withJdbcTemplate(jdbcTemplate ->
                jdbcTemplate.update(connection -> {
                    val preparedStatement = connection.prepareStatement(INSERT_COUNTRY_SQL);
                    preparedStatement.setString(1, country.getName());
                    preparedStatement.setString(2, country.getCodeName());
                    return preparedStatement;
                }, keyHolder));

        country.setId(
                Optional.ofNullable(keyHolder.getKey())
                        .map(Number::longValue)
                        .orElseThrow(() -> new RuntimeException("No key was generated!")));
    }

    @Override
    public Stream<Country> getAllCountries() {
        return mapJdbcTemplate(jdbcTemplate ->
                jdbcTemplate
                        .query(GET_ALL_COUNTRIES_SQL, COUNTRY_ROW_MAPPER)
                        .stream());
    }

    @Override
    public List<Country> getCountryListStartWith(String name) {
        return mapNamedParameterJdbcTemplate(namedParameterJdbcTemplate ->
                namedParameterJdbcTemplate.query(
                        GET_COUNTRIES_BY_NAME_SQL,
                        new MapSqlParameterSource("name", name + "%"),
                        COUNTRY_ROW_MAPPER));
    }

    @Override
    public void updateCountryName(String codeName, String newCountryName) {
        withJdbcTemplate(jdbcTemplate ->
                jdbcTemplate.update(
                        String.format(UPDATE_COUNTRY_NAME_SQL, newCountryName, codeName)));
    }

    @Override
    public void loadCountries() {
        withJdbcTemplate(jdbcTemplate ->
                Arrays.stream(COUNTRY_INIT_DATA)
                        .forEach(countryData -> jdbcTemplate.execute(
                                String.format(LOAD_COUNTRIES_SQL, countryData[0], countryData[1]))));
    }

    @Override
    public Optional<Country> getCountryByCodeName(@NotNull String codeName) {
        val sql = String.format(GET_COUNTRY_BY_CODE_NAME_SQL, codeName);
        return mapJdbcTemplate(jdbcTemplate -> Optional.of(
                jdbcTemplate.query(sql, COUNTRY_ROW_MAPPER).get(0)));
    }

    @Override
    public void remove(Country exampleCountry) {
        val sql = String.format(REMOVE_COUNTRY_SQL, exampleCountry.getId());
        withJdbcTemplate(jdbcTemplate -> jdbcTemplate.execute(sql));
    }

    @Override
    public Optional<Country> getCountryByName(@NotNull String name) {
        val sql = String.format(GET_COUNTRY_BY_NAME_SQL, name);
        return mapJdbcTemplate(jdbcTemplate ->
                jdbcTemplate.query(sql, COUNTRY_ROW_MAPPER).stream()
                        .findAny());
    }
}
