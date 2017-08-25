package lab.dao.jpa;

import lab.dao.CountryDao;
import lab.model.Country;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class CountryJpaDaoImpl extends AbstractJpaDao implements CountryDao {

    @Override
    public void save(@NotNull Country country) {
        EntityManager em = lcemf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(country);
        transaction.commit();
        if (em != null) {
            em.close();
        }
    }

    @Override
    public Stream<Country> getAllCountries() {
        List<Country> countryList = null;
        EntityManager em = lcemf.createEntityManager();
        if (em != null) {
            countryList =
                    em.createQuery("from Country", Country.class)
                            .getResultList();
            em.close();
        }
        return countryList.stream();
    }

    @Override
    public Optional<Country> getCountryByName(@NotNull String name) {

        EntityManager em = lcemf.createEntityManager();

        Country country = em
                .createQuery("SELECT c FROM Country c WHERE c.name LIKE :name",
                        Country.class).setParameter("name", name)
                .getSingleResult();


        return Optional.of(country);
    }

    @Override
    public void remove(Country exampleCountry) {

        EntityManager em = lcemf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(em.merge(exampleCountry));
        transaction.commit();
        if (em != null) {
            em.close();
        }
        // TODO: 23/08/2017 realize it!
    }

}
