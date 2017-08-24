package lab.dao.jpa;

import lab.dao.CountryDao;
import lab.model.Country;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class CountryJpaDaoImpl extends AbstractJpaDao implements CountryDao {

    @Override
    public void save(@NotNull Country country) {
        runWithEntityManager((em) -> em.persist(country));
    }

    @Override
    public Stream<Country> getAllCountries() {
        return doWithEntityManager((em) -> em
                .createQuery("from " + Country.class.getName(), Country.class)
                .getResultList()
                .stream());
    }

    @Override
    public Optional<Country> getCountryByName(@NotNull String name) {
        final String ql = "SELECT c FROM " + Country.class.getName() + " c WHERE c.name LIKE :name";
        return doWithEntityManager((em) -> Optional.ofNullable(em
                .createQuery(ql, Country.class).setParameter("name", name)
                .getSingleResult()));
    }

    @Override
    public void remove(Country exampleCountry) {
        runWithEntityManager((em) -> em.remove(em.merge(exampleCountry)));
    }

}
