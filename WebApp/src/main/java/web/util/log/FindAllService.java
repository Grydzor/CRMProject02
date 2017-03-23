package web.util.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FindAllService {
    private final Logger log = LogManager.getLogger(FindAllService.class);

    @Pointcut("execution(* web.service.*.findAll(..))")
    private void pointcutRead() {
    }

    @Before("pointcutRead()")
    private void beforeRead(JoinPoint joinPoint) {
        log.info("Запуск метода поиска "
                + joinPoint.getSignature().toShortString());
    }


    @AfterReturning(pointcut = "pointcutRead()", returning="returnObject")
    private void afterReturningRead(Object returnObject) {
        log.info("Успешный поиск " + returnObject);
    }

    @AfterThrowing(pointcut = "pointcutRead()", throwing = "exc")
    private void afterThrowingRead(Throwable exc) {
        log.info("Ошибка поиска" + exc) ;
    }
}
