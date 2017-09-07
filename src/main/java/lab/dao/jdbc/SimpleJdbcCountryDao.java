package lab.dao.jdbc;

import io.vavr.CheckedConsumer;
import io.vavr.CheckedFunction1;
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

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Component
//@Qualifier("jdbcCountryDao")
public class SimpleJdbcCountryDao extends NamedParameterJdbcDaoSupport implements JdbcCountryDao {

    private static final RowMapper<Country> COUNTRY_ROW_MAPPER = (resultSet, rowNum) ->
            new SimpleCountry(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("code_name"));

    private final BiConsumer<String, String> addCountry = (name, codeName) -> withJdbcTemplate(jdbcTemplate ->
            jdbcTemplate.execute(String.format(
                    "INSERT INTO country (name, code_name) VALUES ('%s', '%s')", name, codeName)));

    private final Supplier<Stream<Country>> getCountries = () -> mapJdbcTemplate(jdbcTemplate ->
            jdbcTemplate.query("SELECT id, name, code_name FROM country", COUNTRY_ROW_MAPPER).stream());

    private final Function<String, Stream<Country>> getCountriesByNameLike = name -> mapNamedParameterJdbcTemplate(
            namedParameterJdbcTemplate ->
                    namedParameterJdbcTemplate.query(
                            "SELECT id, name, code_name FROM country WHERE name LIKE :name",
                            new MapSqlParameterSource("name", name),
                            COUNTRY_ROW_MAPPER)
                            .stream());

    private final Function<String, Stream<Country>> getCountriesByName = name -> mapJdbcTemplate(jdbcTemplate ->
            jdbcTemplate.query(
                    String.format("SELECT id, name, code_name FROM country WHERE name = '%s'", name),
                    COUNTRY_ROW_MAPPER)
                    .stream());

    private final Function<String, Stream<Country>> getCountriesByCodeName = codeName -> mapJdbcTemplate(jdbcTemplate ->
            jdbcTemplate.query(
                    String.format("SELECT id, name, code_name FROM country WHERE code_name = '%s'", codeName),
                    COUNTRY_ROW_MAPPER)
                    .stream());

    private static final String UPDATE_COUNTRY_NAME_SQL = "UPDATE country SET name='%s' WHERE code_name='%s'";
    private static final String INSERT_COUNTRY_SQL = "INSERT INTO country (name, code_name) VALUES (?, ?)";

    @SneakyThrows
    private <T> T mapJdbcTemplate(CheckedFunction1<JdbcTemplate, T> jdbcTemplateMapper) {
        val jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate != null)
            return jdbcTemplateMapper.apply(jdbcTemplate);
        else
            throw new RuntimeException("DB has not initialized!");
    }

    @SneakyThrows
    private void withJdbcTemplate(CheckedConsumer<JdbcTemplate> jdbcTemplateConsumer) {
        val jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate != null)
            jdbcTemplateConsumer.accept(jdbcTemplate);
        else
            throw new RuntimeException("DB has not initialized!");
    }

    @SneakyThrows
    private <T> T mapNamedParameterJdbcTemplate(CheckedFunction1<NamedParameterJdbcTemplate, T> namedParameterJdbcTemplateMapper) {
        val namedParameterJdbcTemplate = getNamedParameterJdbcTemplate();
        if (namedParameterJdbcTemplate != null)
            return namedParameterJdbcTemplateMapper.apply(namedParameterJdbcTemplate);
        else
            throw new RuntimeException("DB has not initialized!");
    }

    @SneakyThrows
    private void withNamedParameterJdbcTemplate(CheckedConsumer<NamedParameterJdbcTemplate> namedParameterJdbcTemplateConsumer) {
        val namedParameterJdbcTemplate = getNamedParameterJdbcTemplate();
        if (namedParameterJdbcTemplate != null)
            namedParameterJdbcTemplateConsumer.accept(namedParameterJdbcTemplate);
        else
            throw new RuntimeException("DB has not initialized!");
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

        country.setId(keyHolder.getKey().longValue());
    }

    @Override
    public Stream<Country> getAllCountries() {
        return getCountries.get();
    }

    @Override
    public List<Country> getCountryListStartWith(String name) {
        return getCountriesByNameLike.apply(name + "%").collect(Collectors.toList());
    }

    @Override
    public void updateCountryName(String codeName, String newCountryName) {
        withJdbcTemplate(jdbcTemplate ->
                jdbcTemplate.update(
                        String.format(UPDATE_COUNTRY_NAME_SQL, newCountryName, codeName)));
    }

    @Override
    public void loadCountries() {
        for (String[] countryData : COUNTRY_INIT_DATA)
            addCountry.accept(countryData[0], countryData[1]);
    }

    @Override
    public Optional<Country> getCountryByCodeName(@NotNull String codeName) {
        return getCountriesByCodeName.apply(codeName).findFirst();
    }

    @Override
    public void remove(Country exampleCountry) {
        // TODO: 23/08/2017
    }

    @Override
    public Optional<Country> getCountryByName(@NotNull String name) {
        return getCountriesByName.apply(name).findAny();
    }
}
