package kr.ac.hansung.cse.gjmarekt.service;

import kr.ac.hansung.cse.gjmarekt.dto.SignUpDTO;
import kr.ac.hansung.cse.gjmarekt.entity.GJUser;
import kr.ac.hansung.cse.gjmarekt.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public SignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void signUpProcess(SignUpDTO signUpDTO) {
        String email = signUpDTO.getEmail();
        String password = signUpDTO.getPassword();

        // 유저 중복 확인
        Boolean isExists = userRepository.existsByEmail(email);

        if(isExists) {
            return;
        }

        GJUser gjUser = new GJUser();

        gjUser.setEmail(email);
        gjUser.setPassword(passwordEncoder.encode(password));

        userRepository.save(gjUser);
    }
}
