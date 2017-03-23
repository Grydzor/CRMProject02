package web.util.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ReadService{
    private final Logger log = LogManager.getLogger(ReadService.class);

    @Pointcut("execution(* web.service.*.read(..))")
    private void pointcutRead() {
    }

    @Before("pointcutRead()")
    private void beforeRead(JoinPoint joinPoint) {
        Object[] objectArgs = joinPoint.getArgs();
        String args = "";
        for (Object i : objectArgs) {
            args += i;
        }
        log.info("Запуск метода чтения " + args
                + " метода : "
                + joinPoint.getSignature().toString());
    }

    @AfterReturning(pointcut = "pointcutRead()", returning="returnObject")
    private void afterReturningRead(Object returnObject) {
        log.info("Успешное чтение " +
                returnObject);
    }

    @AfterThrowing(pointcut = "pointcutRead()", throwing = "exc")
    private void afterThrowingRead(Throwable exc) {
        log.info("Ошибка чтения" + exc) ;
    }

}

