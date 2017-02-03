package ru.dm.shop.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Denis on 23/03/2016.
 */
@Aspect
public class ServiceAspect {
    private static Logger log = Logger.getLogger(ServiceAspect.class);

    @Before("execution(* ru.dm.shop.service.impl.*.*(..))")
    public void serviceLogging(JoinPoint joinPoint) {
        log.info(new Date()
                + " Start invocation of service method"
                + joinPoint.getTarget().getClass().getSimpleName()
                + "."
                + joinPoint.getSignature().getName()
                + " with params:\n"
                + Arrays.toString(joinPoint.getArgs()));
    }

}