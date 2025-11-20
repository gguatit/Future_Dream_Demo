package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 회원가입
     * @return 성공 시 true, 중복 시 false
     */
    public boolean registerUser(String username, String password, String email) {
        // 중복 체크
        if (userRepository.existsByUsername(username)) {
            return false;  // 이미 존재하는 사용자명
        }

        // 새 사용자 저장
        User user = new User(username, password, email);
        userRepository.save(user);
        return true;
    }

    /**
     * 로그인 검증
     * @return 성공 시 User 객체, 실패 시 null
     */
    public User authenticateUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // 비밀번호 확인
            if (user.getPassword().equals(password)) {
                return user;  // 로그인 성공
            }
        }

        return null;  // 로그인 실패
    }
}