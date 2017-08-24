package lab.dao.jpa;

import lab.dao.CountryDao;
import lab.model.Country;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class CountryJpaDaoImpl extends AbstractJpaDao implements CountryDao {

    @Override
    public void save(@NotNull Country country) {
        Optional.ofNullable(emf.createEntityManager())
                .ifPresent(em -> {
                    EntityTransaction transaction = em.getTransaction();
                    transaction.begin();
                    em.merge(country);
                    transaction.commit();
                    em.close();
                });
    }

    @Override
    public Stream<Country> getAllCountries() {
        Optional<EntityManager> entityManager = Optional.ofNullable(emf.createEntityManager());

        Stream<Country> countries =
                entityManager
                        .map(em -> em
                                .createQuery("from Country", Country.class)
                                .getResultList())
                        .map(List::stream)
                        .orElseGet(Stream::empty);

        entityManager.ifPresent(EntityManager::close);

        return countries;
    }

    @Override
    public Optional<Country> getCountryByName(@NotNull String name) {
        Optional<EntityManager> entityManager = Optional.ofNullable(emf.createEntityManager());

        Optional<Country> country =
                entityManager
                        .map(em -> em
                                .createQuery("SELECT c FROM Country c WHERE c.name LIKE :name",
                                        Country.class).setParameter("name", name)
                                .getSingleResult());

        entityManager.ifPresent(EntityManager::close);

        return country;
    }

    @Override
    public void remove(Country exampleCountry) {
        Optional<EntityManager> entityManager = Optional.ofNullable(emf.createEntityManager());

        entityManager.ifPresent(em -> em.detach(exampleCountry));
        entityManager.ifPresent(EntityManager::close);
    }

}
