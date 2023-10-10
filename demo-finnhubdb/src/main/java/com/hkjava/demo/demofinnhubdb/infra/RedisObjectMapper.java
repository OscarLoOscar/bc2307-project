package com.hkjava.demo.demofinnhubdb.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.swagger.v3.oas.annotations.Operation;

public class RedisObjectMapper {

  @Operation(
      summary = "ParameterNamesModule: This module adds support for serializing and deserializing parameter names."
          + //
          "Jdk8Module: This module adds support for serializing and deserializing Java 8 features, such as lambdas and streams."
          + //
          "JavaTimeModule: This module adds support for serializing and deserializing Java 8 date and time types."
          + //
          "")
  public static ObjectMapper of() {
    ObjectMapper redisObjectMapper = new ObjectMapper()//
        .registerModules(new ParameterNamesModule())//
        .registerModule(new Jdk8Module())//
        .registerModule(new JavaTimeModule());
    return redisObjectMapper;
  }
}
/*
 * ObjectMapper redisObjectMapper = RedisHelper.of();
 * 
 * serialize Java to Json : Object object = new Object(); String json = redisObjectMapper.writeValueAsString(object);
 * 
 * Deserialize Json to Java : String json = "{\"name\": \"John Doe\"}"; Object object = redisObjectMapper.readValue(json, Object.class);
 * 
 */
