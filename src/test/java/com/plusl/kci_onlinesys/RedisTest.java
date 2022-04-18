package com.plusl.kci_onlinesys;

import com.plusl.kci_onlinesys.service.LikeService;
import com.plusl.kci_onlinesys.util.RedisKeyUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;

/**
 * @program: kci_onlinesys
 * @description: Redis测试
 * @author: PlusL
 * @create: 2022-04-14 20:55
 **/
@SpringBootTest
@ContextConfiguration(classes = KciOnlinesysApplication.class)
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LikeService likeService;

    @Test
    public void testString() {
//        String redisKey = "test:count";

//        redisTemplate.opsForValue().set(redisKey, 1);

//        System.out.println(redisTemplate.opsForValue().get(redisKey));
//        redisTemplate.opsForSet().add(1, 11);

        //String entityLikeKey = RedisKeyUtil.getEntityLikeKey(1, 11);
//        boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, 11);
//        System.out.println(isMember);

        //System.out.println(redisTemplate.opsForSet().isMember(entityLikeKey, 1));
//        System.out.println(likeService.findEntityLikeStatus(1,1,11));

        String likeKey = RedisKeyUtil.getUserLikeKey(1);
        Object object = redisTemplate.opsForValue().get(likeKey);
        System.out.println(object);
    }
}
