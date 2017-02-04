package ru.dm.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dm.shop.entity.Order;

/**
 * Created by alt on 04.02.17.
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
