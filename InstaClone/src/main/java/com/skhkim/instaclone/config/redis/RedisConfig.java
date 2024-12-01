package com.skhkim.instaclone.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Autowired
    private RedisSubscriber redisSubscriber;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // Key는 String, Value는 Object 이므로 직렬화 방식을 설정합니다.
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // JSON 직렬화

        // Hash key와 value도 설정
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        container.addMessageListener(onChatMessage(), new PatternTopic("/chat/*"));
        container.addMessageListener(onChatSesson(), new ChannelTopic("websocket-events"));
        container.addMessageListener(onChatInvite(), new PatternTopic("/invite/*"));
        return container;
    }

    @Bean
    public MessageListener onChatMessage() {
        return new MessageListenerAdapter(redisSubscriber, "onMessage");
    }
    @Bean
    public MessageListener onChatSesson() {
        return new MessageListenerAdapter(redisSubscriber, "onSession");
    }
    @Bean
    public MessageListener onChatInvite() {return new MessageListenerAdapter(redisSubscriber, "onInvite");}
}
