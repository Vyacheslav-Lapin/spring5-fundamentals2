package lab.aop;

import lab.model.Person;
import lombok.AllArgsConstructor;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(0)
@Component
public class Politeness {

    @Pointcut("execution(* sellSquishee(..))")
    void sellSquishee() {
    }

    @Before("sellSquishee()")
    public void sayHello(JoinPoint joinPoint) {
        System.out.printf("Hello %s. How are you doing?%n",
                ((Person) joinPoint.getArgs()[0]).getName());
    }

    @AfterReturning(pointcut = "sellSquishee()", returning = "retVal", argNames = "retVal")
    public void askOpinion(Object retVal) {
        System.out.printf("Is %s Good Enough?%n", ((lab.model.Squishee) retVal).getName());
    }

    @AfterThrowing("sellSquishee()")
    public void sayYouAreNotAllowed() {
        System.out.println("Hmmm...");
    }

    @After("sellSquishee()")
    public void sayGoodBye() {
        System.out.println("Good Bye!");
    }

    @Around("sellSquishee()")
    public Object sayPoliteWordsAndSell(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Hi!");
        val retVal = pjp.proceed();
        System.out.println("See you!");
        return retVal;
    }

}