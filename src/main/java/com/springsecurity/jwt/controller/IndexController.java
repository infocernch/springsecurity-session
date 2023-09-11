package com.springsecurity.jwt.controller;

import com.springsecurity.jwt.model.Users;
import com.springsecurity.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //view를 리턴
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping({"", "/"})
    public String index() {
        //머스테치 기본펄터 src/main/resources/
        //뷰리졸버 설정: templates(prepix) .mustache(suffix)
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    //스프링시큐리티가 해당주소를 낚아채버림 - SecurityConfig 파일 생성 후 작동안함..
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(Users users) {
        System.out.println("users = " + users);
        users.setRole("ROLE_USER");
        String rawPassword = users.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);//시큐리티에서는 암호화 해서 넣어야함
        users.setPassword(encPassword);
        userRepository.save(users);
        return "redirect:/loginForm";
    }
}
