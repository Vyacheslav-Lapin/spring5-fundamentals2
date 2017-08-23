package lab.dao.jdbc;

import lab.dao.PersonDao;
import lab.model.Person;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.stream.Stream;

@Log4j2
public class SimplePersonJdbcDao extends JdbcDaoSupport implements PersonDao {

    @Override
    public void insert(Person person) {

        // TODO: extract logging and checking functional to special aspect
        if (person == null) {
            log.debug("Domain person is null!");
            return;
        }

        log.debug("Processing person: {}", person);

//        person.setId(
//                insert(person.getFirstName(),
//                        person.getLastName(),
//                        person.getCountry().getId(),
//                        person.getAge(),
//                        person.getHeight(),
//                        person.isProgrammer(),
//                        person.isBroke()));
    }

    @Override
    public int insert(String firstName,
                      String lastName,
                      int countryId,
                      int age,
                      double height,
                      boolean programmer,
                      boolean broke) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        getJdbcTemplate().update(
                connection -> {

                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO person " +
                                    "(first_name, last_name, country_id, age, height, programmer, broke) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);

                    ps.setString(1, firstName);
                    ps.setString(2, lastName);
                    ps.setInt(3, countryId);
                    ps.setInt(4, age);
                    ps.setDouble(5, height);
                    ps.setBoolean(6, programmer);
                    ps.setBoolean(7, broke);

                    return ps;
                },

                keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public Person select(int id) {

        Person user = null;

//        if (id > 0) {
//            user = getJdbcTemplate().queryForObject(
//                    "SELECT " +
//                            "p.id AS person_id, " +
//                            "p.first_name AS person_first_name, " +
//                            "p.last_name AS person_last_name, " +
//                            "c.id AS country_id, " +
//                            "c.name AS country_name, " +
//                            "c.code_name AS country_code_name, " +
//                            "(SELECT group_concat(value SEPARATOR ',') FROM contact) AS person_contacts, " +
//                            "p.age AS person_age, " +
//                            "p.height AS person_height, " +
//                            "p.programmer AS person_programmer, " +
//                            "p.broke AS person_broke " +
//                            "FROM person p, country c " +
//                            "WHERE p.id = ?",
//                    new Object[]{id},
//                    Person::fromRow);
//        }
        log.debug("Receidved person: {}", user);

        return user;
    }

    @Override
    public Stream<Person> selectAll() {
        return null;
//        return this.getJdbcTemplate().query(
//                "SELECT " +
//                        "p.id AS person_id, " +
//                        "p.first_name AS person_first_name, " +
//                        "p.last_name AS person_last_name, " +
//                        "c.id AS country_id, " +
//                        "c.name AS country_name, " +
//                        "c.code_name AS country_code_name, " +
//                        "(SELECT group_concat(value SEPARATOR ',') FROM contact) AS person_contacts, " +
//                        "p.age AS person_age, " +
//                        "p.height AS person_height, " +
//                        "p.programmer AS person_programmer, " +
//                        "p.broke AS person_broke " +
//                        "FROM person p, country c",
//                Person::fromRow);
    }
}
