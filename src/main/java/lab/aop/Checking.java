package lab.aop;

import lab.service.CustomerBrokenException;
import lab.model.Person;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
public class Checking {

    @Around("Politeness.sellSquishee()")
    public Object checkIsBroke(ProceedingJoinPoint pjp) throws Throwable {
        if (((Person) pjp.getArgs()[0]).isBroke())
            throw new CustomerBrokenException();

        return pjp.proceed();
    }
}
