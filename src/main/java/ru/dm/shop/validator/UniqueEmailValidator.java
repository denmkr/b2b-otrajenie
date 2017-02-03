package ru.dm.shop.validator;

/**
 * Created by Denis on 04.05.16.
 */
import org.springframework.beans.factory.annotation.Autowired;
import ru.dm.shop.entity.User;
import ru.dm.shop.service.UserService;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    UserService userService;

    @Override
    public void initialize(UniqueEmail paramA) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext ctx) {
        if(email == null) {
            return false;
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return true;
        }

        return false;

    }

}