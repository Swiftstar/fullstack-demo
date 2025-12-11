package life.henrytech.backend.dto;

public enum ResponseStatus {
    SUCCESS(200, "成功"),
    CREATED(201, "已建立"),
    BAD_REQUEST(400, "請求錯誤"),
    UNAUTHORIZED(401, "未授權"),
    FORBIDDEN(403, "禁止訪問"),
    NOT_FOUND(404, "找不到資源"),
    CONFLICT(409, "資源衝突"),
    INTERNAL_ERROR(500, "伺服器內部錯誤");
    
    private final int code;
    private final String description;
    
    ResponseStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
}

