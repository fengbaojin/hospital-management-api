package com.example.demo.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author fengxuwang
 * @date 2022/02/28
 */
@Aspect
@Component
public class RabbitMqReceiverAspect {

    private static final Logger logger = LoggerFactory.getLogger(com.example.demo.aop.RabbitMqReceiverAspect.class);

    @Pointcut("@annotation(com.example.demo.aop.annotation.RabbitMessage)")
    public void pointcut() {
    }

    @Around(value = "pointcut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        String message = joinPoint.getArgs()[0].toString();
        try {
            System.out.println(message+"环绕切面");
            joinPoint.proceed();
            //后增强
            System.out.println("正在执行后增强...");
        } catch (Exception e) {
            logger.error("队列执行异常：{}", e.getMessage());
        }
    }
}
