package jdbc;

import lab.dao.jdbc.JdbcCountryDao;
import lab.dao.jdbc.spring.SimpleJdbcCountryDao;
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
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:jdbc.xml")
class JdbcTest {

	@Autowired
	private JdbcCountryDao jdbcCountryDao;
	
    private List<Country> expectedCountryList = new ArrayList<>();
    private List<Country> expectedCountryListStartsWithA = new ArrayList<>();
    private Country countryWithChangedName = new SimpleCountry(8, "Russia", "RU");

    @BeforeEach
    void setUp() throws Exception {
        initExpectedCountryLists();
        jdbcCountryDao.loadCountries();
    }
    
    @Test
    void testCountryList() {
        List<Country> countryList = jdbcCountryDao.getAllCountries()
                .collect(Collectors.toList());

        assertNotNull(countryList);
        assertEquals(expectedCountryList.size(), countryList.size());
        for (int i = 0; i < expectedCountryList.size(); i++)
            assertThat(countryList.get(i), is(expectedCountryList.get(i)));
    }

    @Test
    @DirtiesContext
    void testCountryChange() {
        jdbcCountryDao.updateCountryName("RU", "Russia");
        assertEquals(countryWithChangedName, jdbcCountryDao.getCountryByCodeName("RU"));
    }

    @Test
    void testCountryListStartsWithA() {
        List<Country> countryList = jdbcCountryDao.getCountryListStartWith("A");
        assertNotNull(countryList);
        assertEquals(expectedCountryListStartsWithA.size(), countryList.size());
        for (int i = 0; i < expectedCountryListStartsWithA.size(); i++)
            assertEquals(expectedCountryListStartsWithA.get(i), countryList.get(i));
    }

    private void initExpectedCountryLists() {
         for (int i = 0; i < SimpleJdbcCountryDao.COUNTRY_INIT_DATA.length;) {
             String[] countryInitData = SimpleJdbcCountryDao.COUNTRY_INIT_DATA[i];
             Country country = new SimpleCountry(++i, countryInitData[0], countryInitData[1]);
             expectedCountryList.add(country);
             if (country.getName().startsWith("A")) {
                 expectedCountryListStartsWithA.add(country);
             }
         }
     }
}