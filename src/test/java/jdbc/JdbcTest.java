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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:jdbc.xml")
class JdbcTest {

    @Autowired
    private CountryDao countryDao;

    private List<Country> expectedCountryList = new ArrayList<>();
    private List<Country> expectedCountryListStartsWithA = new ArrayList<>();
    private Country countryWithChangedName = new SimpleCountry(8, "Russia", "RU");

    @BeforeEach
    void setUp() throws Exception {
        expectedCountryList = IntStream.range(0, SimpleCountryJdbcDao.COUNTRY_INIT_DATA.length)
                                       .mapToObj(index -> {
                                           final String[] countryData = SimpleCountryJdbcDao.COUNTRY_INIT_DATA[index];
                                           return new SimpleCountry(index + 1, countryData[0], countryData[1]);
                                       })
                                       .collect(Collectors.toList());
        expectedCountryListStartsWithA = expectedCountryList.stream()
                                                            .filter(country -> country.getName().startsWith("A"))
                                                            .collect(Collectors.toList());
        countryDao.loadCountries();
    }

    @Test
    void testCountryList() {
        final List<Country> countryList = countryDao.getCountryList();
        assertNotNull(countryList);
        assertEquals(expectedCountryList.size(), countryList.size());
        IntStream.range(0, expectedCountryList.size())
                 .forEach(i -> assertEquals(expectedCountryList.get(i), countryList.get(i)));
    }

    @Test
    void testCountryListStartsWithA() {
        final List<Country> countryList = countryDao.getCountryListStartWith("A");
        assertNotNull(countryList);
        assertEquals(expectedCountryListStartsWithA.size(), countryList.size());
        IntStream.range(0, expectedCountryListStartsWithA.size())
                 .forEach(i -> assertEquals(expectedCountryListStartsWithA.get(i), countryList.get(i)));
    }

    @Test
    @DirtiesContext
    void testCountryChange() {
        countryDao.updateCountryName("RU", "Russia");
        assertEquals(countryWithChangedName, countryDao.getCountryByCodeName("RU"));
    }
}
