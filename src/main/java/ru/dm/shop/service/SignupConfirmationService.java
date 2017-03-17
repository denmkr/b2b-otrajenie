package ru.dm.shop.service;

import ru.dm.shop.entity.SignupConfirmation;

import java.util.List;

/**
 * Created by alt on 16.03.17.
 */
public interface SignupConfirmationService {
    SignupConfirmation create(SignupConfirmation signupConfirmation);

    SignupConfirmation delete(SignupConfirmation signupConfirmation);

    SignupConfirmation delete(long id);

    List<SignupConfirmation> findAll();

    SignupConfirmation update(SignupConfirmation signupConfirmation);

    SignupConfirmation findById(long id);

    SignupConfirmation findByCode(String code);
}
