package life.henrytech.backend.controller;

import life.henrytech.backend.dto.ApiResponse;
import life.henrytech.backend.dto.AuthData;
import life.henrytech.backend.dto.LoginRequest;
import life.henrytech.backend.dto.RegisterRequest;
import life.henrytech.backend.dto.ResponseStatus;
import life.henrytech.backend.entity.User;
import life.henrytech.backend.service.UserService;
import life.henrytech.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthData>> register(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(
            request.getUsername(),
            request.getPassword(),
            request.getEmail()
        );
        
        // 生成 JWT token
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        
        AuthData authData = new AuthData(user.getUsername(), user.getEmail(), token);
        return ResponseEntity.status(ResponseStatus.CREATED.getCode())
            .body(ApiResponse.success("註冊成功", authData));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthData>> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        
        // 生成 JWT token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        
        // 獲取用戶資訊
        User user = (User) userService.loadUserByUsername(request.getUsername());
        AuthData authData = new AuthData(user.getUsername(), user.getEmail(), token);
        
        return ResponseEntity.ok(ApiResponse.success("登入成功", authData));
    }
    
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AuthData>> getCurrentUser() {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            User user = (User) userService.loadUserByUsername(authentication.getName());
            AuthData authData = new AuthData(user.getUsername(), user.getEmail(), null);
            return ResponseEntity.ok(ApiResponse.success("已登入", authData));
        }
        return ResponseEntity.status(ResponseStatus.UNAUTHORIZED.getCode())
            .body(ApiResponse.error(ResponseStatus.UNAUTHORIZED.getCode(), "未登入"));
    }
}

