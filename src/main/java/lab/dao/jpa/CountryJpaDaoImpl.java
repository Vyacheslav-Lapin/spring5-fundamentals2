package lab.dao.jpa;

import lab.dao.CountryDao;
import lab.model.Country;
import org.jetbrains.annotations.NotNull;
import org.omg.CORBA.REBIND;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class CountryJpaDaoImpl extends AbstractJpaDao implements CountryDao {

    @Override
    public void save(@NotNull Country country) {
//		TODO: Implement it
        final EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(country);
        transaction.commit();
        em.close();
    }

    @Override
    public Stream<Country> getAllCountries() {
        final EntityManager em = emf.createEntityManager();
        final Stream<Country> countries = em.createQuery("select c from country c", Country.class).
                getResultList().stream();
        em.close();
        return countries;
    }

    @Override
    public Optional<Country> getCountryByName(@NotNull String name) {
        final EntityManager em = emf.createEntityManager();
        Optional<Country> country = Optional.ofNullable(em.createQuery(
                "select c from country c where c.name like :name",
                Country.class).setParameter("name", name)
                .getSingleResult());
        em.close();
        return country;
    }

    @Override
    public void remove(Country exampleCountry) {
        final EntityManager em = emf.createEntityManager();
        final Country country = Optional.ofNullable(em.createQuery(
                "select c from country c where c like:exampleCountry", Country.class).
                setParameter("exampleCountry", exampleCountry).getSingleResult()).orElseThrow(RuntimeException::new);
        final EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(country);
        transaction.commit();
    }

}
