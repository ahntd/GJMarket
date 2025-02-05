package kr.ac.hansung.cse.gjmarekt.service;

import kr.ac.hansung.cse.gjmarekt.dto.SignUpDTO;
import kr.ac.hansung.cse.gjmarekt.dto.UserDTO;
import kr.ac.hansung.cse.gjmarekt.entity.GJRole;
import kr.ac.hansung.cse.gjmarekt.entity.GJUser;
import kr.ac.hansung.cse.gjmarekt.repository.RoleRepository;
import kr.ac.hansung.cse.gjmarekt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void signUpProcess(SignUpDTO signUpDTO) {
        String email = signUpDTO.getEmail();
        String password = signUpDTO.getPassword();
        String nickname = signUpDTO.getNickname();

        // 유저 중복 확인
        Boolean isExists = userRepository.existsByEmail(email);
        if (isExists) {
            return;
        }

        GJUser gjUser = new GJUser();
        gjUser.setEmail(email);
        gjUser.setPassword(passwordEncoder.encode(password));
        gjUser.setNickname(nickname);


        // 기본 Role을 "ROLE_USER"로 설정
        List<GJRole> userRoles = new ArrayList<>();
        GJRole role = findByRolename("ROLE_USER");
        userRoles.add(role);
        gjUser.setRoles(userRoles);
        // "ROLE_USER"가 없는 경우 생성
        if (roleRepository.findByRolename("ROLE_ADMIN").isEmpty()) {
            roleRepository.save(role);
        }

        userRepository.save(gjUser);

    }

    public GJRole findByRolename(String rolename) {
        Optional<GJRole> role = roleRepository.findByRolename(rolename);
        return role.orElseGet(() -> new GJRole(rolename));
    }


    // 회원정보 수정
    public void updateUser(GJUser user) {
        // 이메일로 기존 user를 찾는다.
        GJUser gjUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        System.out.println(gjUser.getEmail());


        // 닉네임, 비밀번호가 null이면 변경을 하지 않고 기존 정보를 유지함
        if (user.getNickname() != null) {
            gjUser.setNickname(user.getNickname());
        }
        if (user.getPassword() != null) {
            gjUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(gjUser);
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(GJUser user) {

        GJUser gjUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        gjUser.getRoles().clear();
        userRepository.save(gjUser);

        userRepository.delete(gjUser);
    }

    // 회원 조회
    public UserDTO findUserById(Integer id) {
        // password를 알려주면 안되니 DTO로 바꾸고 리턴함
        GJUser gjUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        return gjUser != null ? convertUserToDTO(gjUser) : null;
    }

    private UserDTO convertUserToDTO(GJUser user) {
        // DTO로 변환함
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setNickname(user.getNickname());
        return userDTO;
    }
}
