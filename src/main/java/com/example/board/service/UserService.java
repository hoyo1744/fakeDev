package com.example.board.service;

import com.example.board.domain.User;
import com.example.board.dto.CommonErrorCode;
import com.example.board.dto.ErrorCode;
import com.example.board.dto.ErrorResponse;
import com.example.board.dto.UserErrorCode;
import com.example.board.exception.RestApiException;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;


    /**
     * 회원가입
     */
    @Transactional
    public Long join(String name) {
        validationDuplicatedUser(name);
        User user = new User();
        user.changeAuthorData(name);
        userRepository.save(user);
        return user.getId();
    }


    /**
     * 회원 조회
     */
    public User findOne(Long userId) {
        return userRepository.findById(userId).get();
    }

    /**
     * 회원 전체 조회
     */
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    /**
     * 회원 수정
     */
    @Transactional
    public Long updateOne(Long userId, String name) {
        User findUser = userRepository.findById(userId).get();
        findUser.changeAuthorData(name);
        return findUser.getId();
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public Long deleteOne(Long userId) {
        userRepository.deleteById(userId);
        return userId;
    }


    private void validationDuplicatedUser(String name) {
        List<User> findUsers = userRepository.findByName(name);

        if (!findUsers.isEmpty()) {
            log.error("duplicated user, {}", findUsers.get(0).getName());
            throw new RestApiException(UserErrorCode.DUPLICATED_USER);
        }
    }


}
