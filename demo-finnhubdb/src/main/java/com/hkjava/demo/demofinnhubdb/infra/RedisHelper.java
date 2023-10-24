package com.hkjava.demo.demofinnhubdb.infra;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.client.ResourceAccessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.swagger.v3.oas.annotations.Operation;

public class RedisHelper {

  // key value pair, key must be unqiue, most likely String
  private RedisTemplate<String, Object> redisTemplate;

  // This factory is used to create a connection to the Redis server.
  public RedisHelper(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public RedisHelper(RedisConnectionFactory factory) {
    ObjectMapper objectMapper = new ObjectMapper() //
        .registerModule(new ParameterNamesModule())
        .registerModule(new Jdk8Module()) //
        .registerModule(new JavaTimeModule());
    this.redisTemplate = template(factory, objectMapper);
  }

  // takes a RedisConnectionFactory and an ObjectMapper as arguments.
  // The ObjectMapper is used to serialize and deserialize objects to and from JSON.
  public RedisHelper(RedisConnectionFactory factory,
      ObjectMapper redisObjectMapper) {
    this.redisTemplate = template(factory, redisObjectMapper);
  }
  // old
  // public static RedisTemplate<String, Object> template(
  // RedisConnectionFactory factory, ObjectMapper redisObjectMapper) {
  // RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
  // redisTemplate.setConnectionFactory(factory);
  // redisTemplate.setKeySerializer(RedisSerializer.string());
  // redisTemplate.setValueSerializer(RedisSerializer.json());
  // // redisTemplate.setHashKeySerializer(RedisSerializer.string());
  // // redisTemplate.setHashValueSerializer(RedisSerializer.json());
  // redisTemplate.afterPropertiesSet();
  // Jackson2JsonRedisSerializer<Object> serializer =
  // new Jackson2JsonRedisSerializer<>(Object.class);
  // serializer.setObjectMapper(redisObjectMapper);
  // redisTemplate.setValueSerializer(serializer);
  // return redisTemplate;
  // }

  @Operation(
      summary = "The RedisConnectionFactory is used to create a connection to the Redis server."//
          + "The ObjectMapper is used to serialize and deserialize objects to and from JSON."//
          + "create a RedisTemplate object that is customized for your specific needs.")
  public static RedisTemplate<String, Object> template(
      RedisConnectionFactory factory, ObjectMapper redisObjectMapper) { // method name -> bean name
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(factory);
    redisTemplate.setKeySerializer(RedisSerializer.string());
    redisTemplate.setValueSerializer(RedisSerializer.json());
    // redisTemplate.setHashKeySerializer(RedisSerializer.string());
    // redisTemplate.setHashValueSerializer(RedisSerializer.json());
    redisTemplate.afterPropertiesSet();
    Jackson2JsonRedisSerializer<Object> serializer =
        new Jackson2JsonRedisSerializer<>(redisObjectMapper, Object.class);
    redisTemplate.setValueSerializer(serializer);
    return redisTemplate;
  }

  // Sets a key-value pair in Redis.
  public boolean set(String key, Object value) {
    try {
      redisTemplate.opsForValue().set(key, value);
      return true;
    } catch (Exception e) {
      throw new ResourceAccessException("Redis unavailable");
    }
  }

  // Sets a key-value pair in Redis.
  public boolean set(String key, Object value, long time) {
    try {
      System.out.println("set before = " + key + " " + value + " " + time);
      redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
      System.out.println("set after");
      return true;
    } catch (Exception e) {
      System.out.println("redis.set = " + e.getMessage());
      throw new ResourceAccessException(
          "Redis unavailable (set method) msg = " + e.getMessage());
    }
  }

  // Gets the value associated with a key in Redis.
  public Object get(String key) {
    try {
      if (key != null) {
        System.out.println("!!!!!!!!!!!!!!!");
        Object object = redisTemplate.opsForValue().get(key);
        System.out.println("!!!!!!!!!!!!!" + object);
        return object;
      }
      return null;
    } catch (Exception e) {
      throw new ResourceAccessException(
          "Redis unavailable (set method) msg = " + e.getMessage());
    }
  }

  // Sets an expiration time for a key in Redis.
  public boolean expire(String key, long time) {
    try {
      if (time > 0) {
        redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
        return true;
      }
      return false;
    } catch (Exception e) {
      throw new ResourceAccessException("Redis unavailable.");
    }
  }

  public static String formatKey(String head, String source) {
    return head.concat(":").concat(source);
  }
}
/*
 * 
 * 使用 RedisTemplate 直接暴露給外部是一種選擇，但是否應該這樣做取決於你的設計和安全需求。
 * 
 * 以下是一些考慮因素：
 * 
 * 封裝與抽象：封裝可以隱藏內部實作細節，提供一個更簡單、更清晰的介面供外部使用。 這可以幫助降低外部使用者的錯誤風險，同時也可以隨時更改內部實現，而不會影響外部程式碼。
 * 
 * 安全性：如果你直接暴露 RedisTemplate，外部程式碼可以執行各種Redis操作，包括刪除鍵、修改值等。 這可能導致不必要的風險，特別是在多人團隊中，某些操作可能被誤用或濫用。
 * 
 * 業務邏輯：RedisHelper 可以包含更多的業務邏輯和錯誤處理，以確保Redis作業按照你的要求執行。 例如，你可以在 set 方法中加入異常處理來處理Redis不可用的情況。
 * 
 * 擴充性：如果以後需要更改底層Redis實現，你可以在 RedisHelper 中進行這些更改，而無需更改外部使用 RedisTemplate 的程式碼。
 * 
 * 總之，使用 RedisTemplate 直接暴露給外部是可能的，但在許多情況下，封裝它並提供一個更高層級的介面會更有利於程式碼的可維護性和安全性。 選擇哪種方法取決於你的專案需求和偏好。
 */

 