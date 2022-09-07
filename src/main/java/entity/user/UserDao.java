package entity.user;

import java.util.List;

public interface UserDao {
    void saveUser(User user);
    User getUserById(int id);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    List<User> getUsersByPosition(Boolean isManager);
    void updateUser(User user);
    void deleteUser(int id);

}
