package ru.dm.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dm.shop.entity.SignupConfirmation;

/**
 * Created by alt on 16.03.17.
 */

@Repository
public interface SignupConfirmationRepository extends JpaRepository<SignupConfirmation, Long> {
    SignupConfirmation findByCode(String code);
}
