package ru.dm.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dm.shop.entity.User;

import java.util.List;

/**
 * Created by Denis on 14.04.2016.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();
    User findByUsername(String username);
    User findByEmail(String email);

    @Query("SELECT COUNT(user) FROM User user")
    Long countOfUsers();

}
