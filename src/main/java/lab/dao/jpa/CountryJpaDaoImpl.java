package lab.dao.jpa;

import lab.dao.CountryDao;
import lab.model.Country;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class CountryJpaDaoImpl extends AbstractJpaDao implements CountryDao {

    @Override
    public void save(@NotNull Country country) {
//		TODO: Implement it
        EntityManager em = null;

        if (em != null) {
            em.close();
        }
    }

    @Override
    public Stream<Country> getAllCountries() {
//	TODO: Implement it
        return null;
    }

    @Override
    public Optional<Country> getCountryByName(@NotNull String name) {
//		TODO: Implement it

        return null;
    }

    @Override
    public void remove(Country exampleCountry) {
        // TODO: 23/08/2017 realize it!
    }

}
