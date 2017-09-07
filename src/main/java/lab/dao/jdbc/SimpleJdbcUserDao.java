package lab.dao.jdbc;

import lab.dao.UserDao;
import lab.model.User;
import lab.model.simple.SimpleUser;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;
import java.util.Optional;

//@Component
@Log4j2
public class SimpleJdbcUserDao extends JdbcDaoSupport implements UserDao {

    private static final String INSERT_USER = "INSERT INTO user (firstname, lastname) VALUES (?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT id, firstname, lastname FROM user WHERE id = ?";
    private static final String SELECT_USERS = "SELECT id, firstname, lastname FROM user";

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) ->
            new SimpleUser(
                    rs.getLong("id"),
                    rs.getString("firstname"),
                    rs.getString("lastname"));

    @Override
    public void insert(@NotNull User user) {
        log.debug("Processing user: {}", user);
        getJdbcTemplate().update(INSERT_USER, user.getFirstName(), user.getLastName());
    }

    @Override
    public Optional<lab.model.User> select(long id) {
        assert id > 0;

        lab.model.User user = getJdbcTemplate().queryForObject(SELECT_USER_BY_ID, new Object[]{id}, USER_ROW_MAPPER);

        log.debug("Received user: {}", user);

        return Optional.ofNullable(user);
    }

    @Override
    public List<User> selectAll() {
        return getJdbcTemplate().query(SELECT_USERS, USER_ROW_MAPPER);
    }
}
