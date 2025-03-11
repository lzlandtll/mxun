package com.mxun.common.annotation;

import com.mxun.common.constant.RedisConstant;
import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: ip接口访问限制切面,使用的RRateLimiter
 * @Author: liuzhilin
 * @Date: 2025/3/5
 */
@Component
@Aspect
public class IpLimitRequestAspect {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private HttpServletRequest request;

    @Around("@annotation(ipLimitRequest)")
    public Object around(ProceedingJoinPoint joinPoint, IpLimitRequest ipLimitRequest) throws Throwable {
        String clientIp = request.getRemoteAddr();
        String ipLimitKey = String.join(":", RedisConstant.IP_LIMIT_PREFIX, ipLimitRequest.path(), clientIp);
        RRateLimiter ipRateLimiter = redissonClient.getRateLimiter(ipLimitKey);
        if(!ipRateLimiter.isExists()){
            ipRateLimiter.trySetRate(RateType.OVERALL, ipLimitRequest.limit(), ipLimitRequest.expireSecond(), RateIntervalUnit.SECONDS);
        }
        if(ipRateLimiter.tryAcquire()){
            return joinPoint.proceed();
        }else {
            throw new BusinessException(ErrorEnum.SYS_REQUEST_LIMIT_ERROR);
        }
    }
}
