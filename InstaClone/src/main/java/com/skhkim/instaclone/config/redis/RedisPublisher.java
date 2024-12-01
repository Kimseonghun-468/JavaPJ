package com.skhkim.instaclone.config.redis;

import com.skhkim.instaclone.dto.SocketSessionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publishSessionEvent(SocketSessionDTO socketSessionDTO) {
        String REDIS_CHANNEL = "websocket-events";
//        System.out.println("Publish Session Information!");
        redisTemplate.convertAndSend(REDIS_CHANNEL, socketSessionDTO);
    }
}