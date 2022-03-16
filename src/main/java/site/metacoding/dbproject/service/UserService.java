package site.metacoding.dbproject.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.dbproject.domain.user.User;
import site.metacoding.dbproject.domain.user.UserRepository;

@RequiredArgsConstructor // 이렇게 해야 di가 된다. final 도 적어야한다. repository앞에
@Service
public class UserService {

    private final UserRepository userRepository;

    public String 유저네임중복검사(String username) {
        User userEntity = userRepository.mUsernameSameCehck(username);

        if (userEntity == null) {
            return "없어";
        } // 1이면 진행하고 1아니면 진행 안함->자바스크립트 처리 -> 중복체크 -> alert처리 해서 아이디 사용
          // 못하게 해 > fetch로 요청해서 input다 날려버리기
        else {
            return "있어";
        }
    }

    @Transactional
    public void 회원가입(User user) {
        userRepository.save(user);
    }

    public User 로그인(User user) {
        return userRepository.mLogin(user.getUsername(), user.getPassword());
    }

    public User 유저정보보기(Integer id) {
        Optional<User> userOp = userRepository.findById(id);

        if (userOp.isPresent()) {
            User userEntity = userOp.get();
            return userEntity;
        } else {
            return null;
        }
    }

    @Transactional
    public User 유저수정(Integer id, User user) {
        // 1. 영속화 -> dirty checking
        Optional<User> userOp = userRepository.findById(id); // 영속화 됨

        if (userOp.isPresent()) {
            // 영속화 된 것
            User userEntity = userOp.get();
            userEntity.setPassword(user.getPassword());
            userEntity.setEmail(user.getEmail());
            return userEntity;
        } else {
            return null;
            // 영속화 안 되면 아무것도 안한다.
        }
    } // 트랜잭션 종료 + 영속화 되어 있는 것들 전부 더티체킹!(변경감지해서 디비에 flush) -> update
}
