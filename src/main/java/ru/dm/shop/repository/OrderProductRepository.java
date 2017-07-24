package ru.dm.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dm.shop.entity.OrderProduct;

/**
 * Created by alt on 07.02.17.
 */

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
