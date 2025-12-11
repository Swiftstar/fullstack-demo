# Backend - Spring Boot API

這是全端作品集的後端服務，提供認證與玩家相關 API。技術棧：Java 17、Spring Boot 4、Spring Security、JWT、Spring Data JPA、MySQL。

## 功能
- 使用者註冊、登入
- JWT 認證與授權
- 取得目前登入使用者資訊

## 專案結構
```
backend/
├── src/main/java/life/henrytech/backend/   # 程式碼
│   ├── controller/   # AuthController 等 REST API
│   ├── service/      # UserService 等業務邏輯
│   ├── repository/   # JPA Repository
│   ├── entity/       # 資料庫實體
│   └── util/         # JwtUtil 等工具
├── src/main/resources/
│   └── application.properties              # 環境設定
└── build.gradle                            # 依賴與建置設定
```

## 環境需求
- Java 17+
- MySQL 8+
- 以內建 Gradle Wrapper 執行即可（無需全域安裝 Gradle）

## 設定
編輯 `src/main/resources/application.properties`：
```properties
spring.datasource.url=jdbc:mysql://localhost/fullstack_demo
spring.datasource.username=你的使用者
spring.datasource.password=你的密碼

jwt.secret=請改為至少 256-bit 的隨機字串
jwt.expiration=86400000
```

## 執行
```bash
./gradlew bootRun
# Windows 可使用 gradlew.bat bootRun
```
服務預設埠：`http://localhost:8080`

## 主要 API
- `POST /api/auth/register` 註冊
- `POST /api/auth/login` 登入，回傳 JWT
- `GET /api/auth/me` 取得當前登入使用者

請在需要授權的請求中加入 Header：
```
Authorization: Bearer <JWT>
```

## 測試
```bash
./gradlew test
```

## 開發提示
- JPA `ddl-auto=update` 僅供開發環境使用，上線請改為受控的遷移流程。
- JWT 密鑰務必在生產環境更換為強隨機值，並妥善管理。


