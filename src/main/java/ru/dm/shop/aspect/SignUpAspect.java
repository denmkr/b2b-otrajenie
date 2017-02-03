package ru.dm.shop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.validation.BindingResult;

/**
 * Created by Denis on 23/03/2016.
 */
@Aspect
public class SignUpAspect {

    @Around("execution(* ru.dm.shop.controller.SignUpController.signup(..))")
    public Object serviceLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        BindingResult result = (BindingResult) joinPoint.getArgs()[1];
        if (result.hasErrors()) {
            return "signup";
        }
        return joinPoint.proceed();
    }

}