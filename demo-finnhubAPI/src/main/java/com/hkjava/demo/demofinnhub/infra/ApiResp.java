package com.hkjava.demo.demofinnhub.infra;

import io.swagger.v3.oas.annotations.media.Schema;

public class ApiResp<T> {
  // attribute name by default same as JSON field name after serialziation
  @Schema(description = "Code For System  Response Cat")
  private int code;

  @Schema(description = "Message to indicate error")
  private String message;

  @Schema(description = "Response Data Body")
  private T data;

  public int getCode() {
    return this.code;
  }

  public String getMessage() {
    return this.message;
  }

  public T getData() {
    return this.data;
  }

  public static <T> ApiRespBuilder<T> builder() {
    return new ApiRespBuilder<>();
  }

  private ApiResp(ApiRespBuilder<T> builder) {
    this.code = builder.code;
    this.message = builder.message;
    this.data = builder.data;
  }

  public static class ApiRespBuilder<T> {
    private int code;
    private String message;
    private T data;

    public ApiRespBuilder<T> status(Code code) {
      this.code = code.getCode();
      this.message = code.getDesc();
      return this;
    }

    public ApiRespBuilder<T> concatMessageIfPresent(String str) {
      if (this.message != null && str != null)
        this.message += " " + str;
      return this;
    }

    public ApiRespBuilder<T> ok() {
      this.code = Code.OK.getCode();
      this.message = Code.OK.getDesc();
      return this;
    }

    public ApiRespBuilder<T> data(T data) {
      this.data = data;
      return this;
    }

    public ApiResp<T> build() {
      if (this.code == 0 || this.message == null)
        throw new RuntimeException();
      return new ApiResp<>(this);
    }

  }
}

// {
// "code" : 200,
// "message" : "OK",
// "data" : [

// ],
// "error" : [
// "", ""
// ],
// }
