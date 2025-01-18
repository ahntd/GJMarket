package kr.ac.hansung.cse.gjmarekt.controller;


import kr.ac.hansung.cse.gjmarekt.dto.SignUpDTO;
import kr.ac.hansung.cse.gjmarekt.service.SignUpService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class SignUpController {

    private final SignUpService signUpService;

    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping("/api/signup")
    public String signUpProcess(SignUpDTO signUpDTO) {

        signUpService.signUpProcess(signUpDTO);

        return "ok";
    }
}
