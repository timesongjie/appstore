package com.bbkmobile.iqoo.interfaces.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformceMonitorAroundAspect {

    @Around(value = "target(com.bbkmobile.iqoo.interfaces.recommendation.dao.RecommendationDAOImpl)")
    public Object monitor(ProceedingJoinPoint pjp) {
        PerformanceMonitor.begin(pjp.getSignature().getName());
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            PerformanceMonitor.end();
        }
        return null;
    }
}
