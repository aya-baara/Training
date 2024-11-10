package com.example.CRUDdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component

public class DemoLoggingAspect {
    private Logger myLogger=Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.example.CRUDdemo.rest.*.*(..))")
    private void foControllerPac(){

    }

    @Pointcut("execution(* com.example.CRUDdemo.service.*.*(..))")
    private void forServicePac(){}

    @Pointcut("execution(* com.example.CRUDdemo.dao.*.*(..))")
    private void forDAOPac(){
    }

    @Pointcut("foControllerPac() ||forServicePac()||forDAOPac()")
    private void forAppFlow(){}


    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint){
        Signature signature=(Signature)joinPoint.getSignature();
        System.out.println(signature.toShortString());

        System.out.println("=======> in @Before method ="+  signature.toShortString());

        Object[]args=joinPoint.getArgs();
        for(Object temp:args){
            myLogger.info("===========>>arguments"+temp.toString());
        }

    }
}

