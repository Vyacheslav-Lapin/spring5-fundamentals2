package orm;

import lab.dao.CountryDao;
import lab.model.Country;
import lab.model.simple.SimpleCountry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Illustrates basic use of Hibernate as a JPA provider.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:orm.xml")
class CountryDaoImplTest {

    private Country exampleCountry =
            new SimpleCountry(0, "Australia", "AU");

    @Autowired
    @Qualifier("countryJpaDaoImpl")
    private CountryDao countryDao;

    @Test
    void testSaveCountry2() {
        countryDao.save(exampleCountry);
        Optional<Country> country = countryDao.getAllCountries().findFirst();
        if (country.isPresent()) {
            assertThat(country.get(), is(exampleCountry));
        } else {
            fail("Country is null");
        }
        countryDao.remove(exampleCountry);
    }

    @Test
    void testGetAllCountries() {
        SimpleCountry country = new SimpleCountry(0, "Canada", "CA");
        countryDao.save(country);
        assertEquals(1, countryDao.getAllCountries().count());
        countryDao.remove(country);
    }

    @Test
    void testGetCountryByName() {
        countryDao.save(exampleCountry);
        assertEquals(exampleCountry.getName(),
                countryDao.getCountryByName("Australia")
                        .orElseThrow(RuntimeException::new).getName());
        countryDao.remove(exampleCountry);
    }

    @Test
    void removeCountry() {
        countryDao.save(exampleCountry);
        countryDao.remove(exampleCountry);
        assertEquals(0, countryDao.getAllCountries().count());
    }

}
