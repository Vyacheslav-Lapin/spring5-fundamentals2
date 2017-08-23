package lab.dao.jdbc;

import lab.dao.CountryDao;
import lab.model.Country;

import java.util.List;

public interface JdbcCountryDao extends CountryDao {
    
    String[][] COUNTRY_INIT_DATA = {
            {"Australia", "AU"},
            {"Canada", "CA"},
            {"France", "FR"},
            {"Hong Kong", "HK"},
            {"Iceland", "IC"},
            {"Japan", "JP"},
            {"Nepal", "NP"},
            {"Russian Federation", "RU"},
            {"Sweden", "SE"},
            {"Switzerland", "CH"},
            {"United Kingdom", "GB"},
            {"United States", "US"}};

    List<Country> getCountryListStartWith(String name);

    void updateCountryName(String codeName, String newCountryName);

    void loadCountries();
}
