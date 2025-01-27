package kr.ac.hansung.cse.gjmarekt.controller;


import kr.ac.hansung.cse.gjmarekt.dto.SignUpDTO;
import kr.ac.hansung.cse.gjmarekt.entity.GJUser;
import kr.ac.hansung.cse.gjmarekt.jwt.JWTUtil;
import kr.ac.hansung.cse.gjmarekt.service.SignUpService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class UserController {

    private final SignUpService signUpService;
    private final JWTUtil jwtUtil;


    public UserController(SignUpService signUpService, JWTUtil jwtUtil) {
        this.signUpService = signUpService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/api/signup")
    public String signUpProcess(SignUpDTO signUpDTO) {

        signUpService.signUpProcess(signUpDTO);

        return "ok";
    }

    //회원 정보 수정
    @PutMapping("/api/updateuser")
    public String updateUserProcess(
            @RequestHeader("Authorization") String authorization,
            SignUpDTO signUpDTO) {
        // 본인이 맞는지 확인 필요
        System.out.println(authorization);
        String token = authorization.split(" ")[1];
        GJUser updatedUser = new GJUser();
        // GJUser 객체를 만들어서 보낸다
        // 토큰으로 이메일을 설정한다.
        updatedUser.setEmail(jwtUtil.getEmail(token));
        updatedUser.setPassword(signUpDTO.getPassword());
        updatedUser.setNickname(signUpDTO.getNickname());
        signUpService.updateUser(updatedUser);

        return "updated";
    }

    // 회원 탈퇴
    @DeleteMapping("/api/deleteuser")
    public String deleteUserProcess(
            @RequestHeader("Authorization") String authorization) {



        return "deleteduser";
    }
}
