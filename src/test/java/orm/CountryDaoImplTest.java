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

/**
 * Illustrates basic use of Hibernate as a JPA provider.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:orm.xml")
class CountryDaoImplTest {

    private Country exampleCountry =
            new SimpleCountry(1, "Canada", "CA");

    @Autowired
    @Qualifier("countryJpaDaoImpl")
    private CountryDao countryDao;

    @Test
    void testSaveCountry2() {
        countryDao.save(exampleCountry);
        assertThat(Optional.of(exampleCountry),
                is(countryDao.getAllCountries().findFirst()));
        countryDao.remove(exampleCountry);
    }

    @Test
    void testGetAllCountries() {
        countryDao.save(exampleCountry);
        assertEquals(1, countryDao.getAllCountries().count());
        countryDao.remove(exampleCountry);
    }

    @Test
    void testGetCountryByName() {
        countryDao.save(exampleCountry);
        assertEquals(exampleCountry,
                countryDao.getCountryByName("Canada")
                        .orElseThrow(RuntimeException::new));
        countryDao.remove(exampleCountry);
    }

}
