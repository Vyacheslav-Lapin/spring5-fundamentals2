package lab.dao;

import lab.model.Country;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public interface CountryDao {

	void save(@NotNull Country country);

	Stream<Country> getAllCountries();

	default Optional<Country> getCountryByName(@NotNull String name) {
		return getAllCountries()
				.filter(country -> country.getName().equals(name))
				.findAny();
	}

	default Optional<Country> getCountryByCodeName(@NotNull String codeName) {
		return getAllCountries()
				.filter(country -> country.getCodeName().equals(codeName))
				.findAny();
	}

	void remove(Country exampleCountry);
}