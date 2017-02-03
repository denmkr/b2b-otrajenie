package ru.dm.shop.validator;

/**
 * Created by Denis on 04.05.16.
 */

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {

    String message() default "Данный логин уже существует";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}