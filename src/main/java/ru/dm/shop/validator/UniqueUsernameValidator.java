package ru.dm.shop.validator;

/**
 * Created by Denis on 04.05.16.
 */
import org.springframework.beans.factory.annotation.Autowired;
import ru.dm.shop.entity.User;
import ru.dm.shop.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    UserService userService;

    @Override
    public void initialize(UniqueUsername paramA) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext ctx) {
        if(username == null) {
            return false;
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            return true;
        }

        return false;

    }

}