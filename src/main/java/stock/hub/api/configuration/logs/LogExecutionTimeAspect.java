package stock.hub.api.configuration.logs;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Log4j2
@Aspect
@Component
public class LogExecutionTimeAspect {

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint jointPoint) throws Throwable {
        final long start = System.currentTimeMillis();

        log.info("{} - STARTED args: \n{}", jointPoint.getSignature(), Arrays.toString(jointPoint.getArgs()));

        Object result = null;

        try {
            result = jointPoint.proceed();
        } catch (Throwable t) {
            log.error("{} - An exception occurred", jointPoint.getSignature(), t);
            throw t;
        }

        log.info("{} - FINISHED (took: {} ms)", jointPoint.getSignature(), System.currentTimeMillis() - start);

        return result;
    }

}
