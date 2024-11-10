package com.example.aopdemo.aspect;

import com.example.aopdemo.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Order(1)
public class MyAspect {



    @Before("com.example.aopdemo.aspect.MyExp.forPackage()")
    public void beforeAddAccountAdvice(JoinPoint joinPoint){

        System.out.println("before adding account");
        MethodSignature methodSignature=(MethodSignature) joinPoint.getSignature();
        System.out.println("Signature  " +methodSignature);
        Object []args=joinPoint.getArgs();
        for (Object temp:args){
            System.out.println(temp);
        }
    }

    @AfterReturning(
            pointcut = "execution(* com.example.aopdemo.dao.AccountDAO.findAccount (..))",
            returning = "result")
    public void afetReturningFindAccount(List<Account>result,JoinPoint joinPoint){
        System.out.println("@ afterRunning methods "+joinPoint.getSignature().toShortString());
        System.out.println("@After Running results : "+result);
    }
}
