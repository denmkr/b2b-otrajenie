package ru.dm.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dm.shop.entity.Order;
import ru.dm.shop.entity.User;

import java.util.List;

/**
 * Created by alt on 04.02.17.
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);

    @Query("SELECT COUNT(*) from Order order WHERE EXTRACT(DAY FROM date) = EXTRACT(DAY FROM current_timestamp)")
    Long countOfOrdersToday();
}
