package lab.dao;

import lab.model.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface UserDao {
	
	void insert(@NotNull User user);

	default Optional<User> select(long id) {
		return selectAll().stream()
				.filter(user -> user.getId() == id)
				.findFirst();
	}
	
	List<User> selectAll();
	
}
