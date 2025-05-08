package io.aiven.spring.mysql.demo.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    @JsonProperty
    public boolean isSuccess() {
        return success;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @JsonProperty
    public T getData() {
        return data;
    }


    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Operation successful", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static ApiResponse<?> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
}