package ru.dm.shop.service;

import ru.dm.shop.entity.User;
import java.util.List;

/**
 * Created by Denis on 17.04.16.
 */

public interface UserService {
    User create(User user);
    User delete(long id);
    List<User> findAll();
    User findById(long id);
    User findByUsername(String username);
    User findByEmail(String email);
    Long countOfUsers();
    User update(User user);
}