package kr.ac.hansung.cse.gjmarekt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class ApiMainController {
    @GetMapping("/api/main")
    public String main() {
        return "main";
    }

}
