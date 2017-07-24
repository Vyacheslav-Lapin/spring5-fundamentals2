package lab.aop;

import lab.model.Person;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class Politeness {

    @Before("execution(* sellSquishee(..))")
    public void sayHello(JoinPoint joinPoint) {
        AopLog.append(String.format("Hello %s. How are you doing?%n", ((Person) joinPoint.getArgs()[0]).getName()));
    }

    @AfterReturning(pointcut = "execution(* sellSquishee(..))",
            returning = "retVal", argNames = "retVal")
    public void askOpinion(Object retVal) {
        AopLog.append("Is " + ((Squishee) retVal).getName() + " Good Enough?%n");
    }

    @AfterThrowing("execution(* sellSquishee(..))")
    public void sayYouAreNotAllowed() {
        AopLog.append("Hmmm...%n");
    }

    @After("execution(* sellSquishee(..))")
    public void sayGoodBye() {
        AopLog.append("Good Bye!%n");
    }

    @Around("execution(* sellSquishee(..))")
    public Object sayPoliteWordsAndSell(ProceedingJoinPoint pjp) throws Throwable {
        AopLog.append("Hi!%n");
        Object retVal = pjp.proceed();
        AopLog.append("See you!%n");
        return retVal;
    }
}
