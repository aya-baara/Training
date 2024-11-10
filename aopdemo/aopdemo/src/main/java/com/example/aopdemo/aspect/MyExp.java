package com.example.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyExp {

    @Pointcut("execution ( * com.example.aopdemo.dao.*.*(..) )")
    public void forPackage(){}
}
