// package com.hkjava.demo.demofinnhubdb.infra;

// import java.util.concurrent.TimeUnit;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
// import org.springframework.data.redis.serializer.RedisSerializer;
// import org.springframework.web.client.ResourceAccessException;
// import com.fasterxml.jackson.databind.ObjectMapper;

// public class RedisHelperT<T> {

//   private RedisTemplate<String, T> redisTemplate;

//   public RedisHelperT(RedisConnectionFactory factory, ObjectMapper redisObjectMapper) {
//     this.redisTemplate = template(factory, redisObjectMapper);
//   }

//   public static <T> RedisTemplate<String, T> template(
//       RedisConnectionFactory factory, ObjectMapper redisObjectMapper) {
//     RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
//     redisTemplate.setConnectionFactory(factory);
//     redisTemplate.setKeySerializer(RedisSerializer.string());
//     redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(T.class));
//     redisTemplate.afterPropertiesSet();
//     return redisTemplate;
//   }

//   public boolean set(String key, T value) {
//     try {
//       redisTemplate.opsForValue().set(key, value);
//       return true;
//     } catch (Exception e) {
//       throw new ResourceAccessException("Redis unavailable");
//     }
//   }

//   public T get(String key) {
//     try {
//       if (key != null) {
//         return redisTemplate.opsForValue().get(key);
//       }
//       return null;
//     } catch (Exception e) {
//       throw new ResourceAccessException("Redis unavailable");
//     }
//   }

//   public boolean expire(String key, long time) {
//     try {
//       if (time > 0) {
//         redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
//         return true;
//       }
//       return false;
//     } catch (Exception e) {
//       throw new ResourceAccessException("Redis unavailable.");
//     }
//   }

//   public static String key(String head,String source){
//     return head.concat(":").concat(source);
//   }
// }
