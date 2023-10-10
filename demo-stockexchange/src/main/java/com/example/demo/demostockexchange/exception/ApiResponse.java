package com.example.demo.demostockexchange.exception;

import com.example.demo.demostockexchange.infra.Code;

public class ApiResponse<T> {
  // attribute name by default same as JSON field name after serialziation
  private int code;
  private String message;
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

  public static <T> ApiResponseBuilder<T> builder() {
    return new ApiResponseBuilder<>();
  }

  private ApiResponse(ApiResponseBuilder<T> builder) {
    this.code = builder.code;
    this.message = builder.message;
    this.data = builder.data;
  }

  public static class ApiResponseBuilder<T> {
    private int code;
    private String message;
    private T data;

    public ApiResponseBuilder<T> status(Code code) {
      this.code = code.getCode();
      this.message = code.getDesc();
      return this;
    }

    public ApiResponseBuilder<T> concatMessageIfPresent(String str) {
      if (this.message != null && str != null)
        this.message += " " + str;
      return this;
    }

    public ApiResponseBuilder<T> ok() {
      this.code = Code.OK.getCode();
      this.message = Code.OK.getDesc();
      return this;
    }

    public ApiResponseBuilder<T> error() {
      this.code = Code.NOTFOUND.getCode();
      this.message = Code.NOTFOUND.getDesc();
      return this;
    }

    public ApiResponseBuilder<T> data(T data) {
      this.data = data;
      return this;
    }

 public ApiResponseBuilder<T> message(String message) {
      this.message = message;
      return this;
    }

    public ApiResponse<T> build() {
      if (this.code == 0 || this.message == null)
        throw new RuntimeException();
      return new ApiResponse<>(this);
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
