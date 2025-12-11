package life.henrytech.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    private String error;
    
    // 成功回應（無資料）
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(ResponseStatus.SUCCESS.getCode(), message, null, null);
    }
    
    // 成功回應（有資料）
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(ResponseStatus.SUCCESS.getCode(), message, data, null);
    }
    
    // 成功回應（僅資料）
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResponseStatus.SUCCESS.getCode(), "操作成功", data, null);
    }
    
    // 錯誤回應
    public static <T> ApiResponse<T> error(int status, String error) {
        return new ApiResponse<>(status, null, null, error);
    }
    
    // 錯誤回應（使用預設狀態碼）
    public static <T> ApiResponse<T> error(String error) {
        return new ApiResponse<>(ResponseStatus.BAD_REQUEST.getCode(), null, null, error);
    }
    
    // 建構函數
    public ApiResponse() {
    }
    
    public ApiResponse(int status, String message, T data, String error) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.error = error;
    }
    
    // Getters and Setters
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
}

