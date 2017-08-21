package jdbc;

import lab.dao.CountryDao;
import lab.dao.spring.jdbc.SimpleCountryJdbcDao;
import lab.model.Country;
import lab.model.simple.SimpleCountry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:jdbc.xml")
class JdbcTest {

	@Autowired
	private CountryDao countryDao;
	
    private List<Country> expectedCountryList = new ArrayList<Country>();
    private List<Country> expectedCountryListStartsWithA = new ArrayList<Country>();
    private Country countryWithChangedName = new SimpleCountry(8, "Russia", "RU");

    @BeforeEach
    void setUp() throws Exception {
        initExpectedCountryLists();
        countryDao.loadCountries();
    }
    
    @Test
    void testCountryList() {
        List<Country> countryList = countryDao.getCountryList();

        assertThat(expectedCountryList, is(countryList));
    }

    @Test
    void testCountryListStartsWithA() {
        List<Country> countryList = countryDao.getCountryListStartWith("A");

        assertThat(expectedCountryListStartsWithA, is(countryList));
    }

    @Test
    @DirtiesContext
    void testCountryChange() {
        countryDao.updateCountryName("RU", "Russia");
        assertEquals(countryWithChangedName, countryDao.getCountryByCodeName("RU"));
    }

    private void initExpectedCountryLists() {
         for (int i = 0; i < SimpleCountryJdbcDao.COUNTRY_INIT_DATA.length;) {
             String[] countryInitData = SimpleCountryJdbcDao.COUNTRY_INIT_DATA[i];
             Country country = new SimpleCountry(++i, countryInitData[0], countryInitData[1]);
             expectedCountryList.add(country);
             if (country.getName().startsWith("A")) {
                 expectedCountryListStartsWithA.add(country);
             }
         }
     }
}