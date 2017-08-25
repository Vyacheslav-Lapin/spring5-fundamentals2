package lab.dao.jpa;

import lab.dao.CountryDao;
import lab.model.Country;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class CountryJpaDaoImpl extends AbstractJpaDao implements CountryDao {

    @Override
    public void save(@NotNull Country country) {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(country);
        transaction.commit();
        em.close();
    }

    @Override
    public Stream<Country> getAllCountries() {
        final Stream<Country> countries;
        final EntityManager em = emf.createEntityManager();
        countries = em.createQuery("from Country", Country.class)
                        .getResultList()
                        .stream();
        em.close();
        return countries;
    }

    @Override
    public Optional<Country> getCountryByName(@NotNull String name) {
        final EntityManager em = emf.createEntityManager();
        final String sql = "SELECT c FROM Country c WHERE c.name LIKE :name";
        final Country country = em.createQuery(sql, Country.class)
                                  .setParameter("name", name)
                                  .getSingleResult();
        return Optional.of(country);
    }

    @Override
    public void remove(Country exampleCountry) {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(em.merge(exampleCountry));
        transaction.commit();
        em.close();
    }
}
