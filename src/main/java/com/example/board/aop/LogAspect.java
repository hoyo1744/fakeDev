package com.example.board.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class LogAspect {

    @Around("com.example.board.aop.Pointcuts.allService() || com.example.board.aop.Pointcuts.allController()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("Start Function! {}", joinPoint.getSignature());

        Object result = joinPoint.proceed();

        log.info("End Function! {}", joinPoint.getSignature());

        return result;
    }

}
