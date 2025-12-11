package life.henrytech.backend.service;

import life.henrytech.backend.dto.ResponseStatus;
import life.henrytech.backend.entity.User;
import life.henrytech.backend.exception.BusinessException;
import life.henrytech.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("使用者不存在: " + username));
        return user;
    }
    
    public User registerUser(String username, String password, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new BusinessException("使用者名稱已存在", ResponseStatus.CONFLICT.getCode());
        }
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException("電子郵件已被使用", ResponseStatus.CONFLICT.getCode());
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setEnabled(true);
        user.setRole("ROLE_USER");
        
        return userRepository.save(user);
    }
}

