package com.mxun.thirdparty;

import com.mxun.common.enums.SmsTypeEnum;
import org.junit.jupiter.api.Test;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@SpringBootTest
class MxunThirdPartyApplicationTests {
    @Autowired
    RedissonClient redissonClient;

    @Test
    public void putCodeRole(){
        String key = "INTERFACES:" + "CHAT_AI.CHAT_SESSION.SEND_MESSAGE";
        Set<Long> roles = new HashSet<>();
        roles.add(1L);
//        roles.add(2L);

        RSet<Object> set = redissonClient.getSet(key);
        set.addAll(roles);
//        set.remove(1L);
        System.out.println(set);
    }

    @Test
    public void registerCode(){
        String key = "SMS:" + SmsTypeEnum.REGISTER.getType() + ":18311516877";
        String value = "9860";

        RBucket<String> bucket = redissonClient.getBucket(key);
        System.out.println(bucket == null);
        System.out.println(bucket.get() == null);
        bucket.set(value, 300, TimeUnit.SECONDS);
        System.out.println(bucket.get() == null);
    }

    @Test
    public void test(){
        String smsKey = String.join(":", "SMS", SmsTypeEnum.REGISTER.getType(), "18311516874");
        RBucket<String> smsBucket = redissonClient.getBucket(smsKey);
        System.out.println(smsBucket + " - " + smsBucket.get());
    }

    @Test
    public void ipRateLimit(){
        String rateLimiterKey = "IP_LIMIT:API1" + ":" + "192.168.22.111";
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(rateLimiterKey);
        if (!rateLimiter.isExists()) {
            rateLimiter.trySetRate(RateType.OVERALL, 1, 10, RateIntervalUnit.SECONDS);
        }

        if (rateLimiter.tryAcquire(1)) {
            System.out.println("Success");
        } else {
            throw new RuntimeException("Too many requests from this IP for this endpoint, please try again later.");
        }
    }

    @Test
    public void clearExpiredLimit(){
        redissonClient.getKeys().getKeysByPattern("IP_LIMIT:*").forEach(key -> {
            System.out.println("key: " + key);
            RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);

            // 这里你需要一种方式来确定rateLimiter是否已经过期
            // 可能需要额外的逻辑或存储额外的时间戳信息
            if (isExpired(rateLimiter)) { // 需要你自己实现isExpired的逻辑
                System.out.println("remove: " + rateLimiter.getName());
                redissonClient.getKeys().delete(rateLimiter.getName());
            }
        });
    }
    // 实现你自己的判断是否过期的逻辑
    private boolean isExpired(RRateLimiter rateLimiter) {
        // 示例逻辑：如果最近一分钟内没有请求，则视为过期
        // 这只是一个示例，实际应用中可能需要更复杂的逻辑
        return rateLimiter.availablePermits() == rateLimiter.getConfig().getRate();
    }


}
