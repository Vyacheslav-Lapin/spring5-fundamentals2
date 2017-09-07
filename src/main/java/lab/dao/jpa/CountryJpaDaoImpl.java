package lab.dao.jpa;

import lab.dao.CountryDao;
import lab.model.Country;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

//@Repository
public class CountryJpaDaoImpl extends AbstractJpaDao implements CountryDao {

    private static final String SELECT_COUNTRY_BY_NAME = "SELECT c FROM Country c WHERE c.name LIKE :name";
    private static final String SELECT_COUNTRY = "SELECT c FROM Country c";

    @Override
    public void save(@NotNull Country country) {
        withEntityManagerInTransaction(entityManager -> entityManager.merge(country));
    }

    @Override
    public Stream<Country> getAllCountries() {
        return mapEntityManager(entityManager ->
                entityManager.createQuery(SELECT_COUNTRY, Country.class)
                        .getResultList()
                        .stream());
    }

    @Override
    public Optional<Country> getCountryByName(@NotNull String name) {
        return Optional.ofNullable(
                mapEntityManager(entityManager ->
                        entityManager.createQuery(SELECT_COUNTRY_BY_NAME, Country.class)
                                .setParameter("name", name)
                                .getSingleResult()));
    }

    @Override
    public void remove(Country exampleCountry) {
        // TODO: 23/08/2017 realize it!
    }

}
