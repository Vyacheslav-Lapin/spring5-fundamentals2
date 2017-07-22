package lab.aop;

import lab.model.Person;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class Politeness {

    @Pointcut("execution(* Bar.sellSquishee(..))")
    public void deal() {
    }

    @Before("deal()")
    public void sayHello(JoinPoint joinPoint) {
        AopLog.append("Hello " + ((Person) joinPoint.getArgs()[0]).getName() + ". How are you doing? \n");
    }

    @AfterReturning(pointcut = "deal()",
            returning = "retVal", argNames = "retVal")
    public void askOpinion(Object retVal) {
        AopLog.append("Is " + ((Squishee) retVal).getName() + " Good Enough? \n");
    }

    @AfterThrowing("deal()")
    public void sayYouAreNotAllowed() {
        AopLog.append("Hmmm... \n");
    }

    @After("deal()")
    public void sayGoodBye() {
        AopLog.append("Good Bye! \n");
    }


    @Around("deal()")
    public Object sayPoliteWordsAndSell(ProceedingJoinPoint pjp) throws Throwable {
        AopLog.append("Hi! \n");
        //we actually don't have to call this method but another aspects could be broken
        Object reternedValue = pjp.proceed();
        AopLog.append("See you! \n");
        return new Squishee("Unusual Squishee");
    }

}