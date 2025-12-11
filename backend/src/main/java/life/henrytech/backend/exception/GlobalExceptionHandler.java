package life.henrytech.backend.exception;

import life.henrytech.backend.dto.ApiResponse;
import life.henrytech.backend.dto.ResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // 處理業務異常
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
        ApiResponse<?> response = ApiResponse.error(e.getStatusCode(), e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(response);
    }
    
    // 處理參數驗證異常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ApiResponse<Map<String, String>> response = ApiResponse.error(
            ResponseStatus.BAD_REQUEST.getCode(), 
            "參數驗證失敗"
        );
        response.setData(errors);
        return ResponseEntity.status(ResponseStatus.BAD_REQUEST.getCode()).body(response);
    }
    
    // 處理認證異常
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleBadCredentialsException(BadCredentialsException e) {
        ApiResponse<?> response = ApiResponse.error(
            ResponseStatus.UNAUTHORIZED.getCode(), 
            "使用者名稱或密碼錯誤"
        );
        return ResponseEntity.status(ResponseStatus.UNAUTHORIZED.getCode()).body(response);
    }
    
    // 處理用戶不存在異常
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        ApiResponse<?> response = ApiResponse.error(
            ResponseStatus.NOT_FOUND.getCode(), 
            e.getMessage()
        );
        return ResponseEntity.status(ResponseStatus.NOT_FOUND.getCode()).body(response);
    }
    
    // 處理所有其他異常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception e) {
        ApiResponse<?> response = ApiResponse.error(
            ResponseStatus.INTERNAL_ERROR.getCode(), 
            "伺服器發生錯誤: " + e.getMessage()
        );
        return ResponseEntity.status(ResponseStatus.INTERNAL_ERROR.getCode()).body(response);
    }
}

