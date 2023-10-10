package com.springsecurity.jwt.controller;

import com.springsecurity.jwt.config.auth.PrincipalDetails;
import com.springsecurity.jwt.model.Users;
import com.springsecurity.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //view를 리턴
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody String loginTest(Authentication authentication,
                                          @AuthenticationPrincipal PrincipalDetails userDetails) {//DI
        System.out.println("==============/test/login================");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principalDetails = " +principalDetails.getUsers());

        System.out.println("userDetails : "+ userDetails.getUsers());
        return "세션 정보 확인하기";
    }

    /*
    *  스프링 시큐리티 : 자기만의 시큐리티 세션을 가지고 있음.( 원래의 세션 영역안에 시큐리티가 관리하는 세션 영역이 있음.)
    *  그 안에 들어 갈 수 있는 타입은 Authentication 객체 뿐임..
    *  컨트롤러에서 DI가 가능.
    *  Authentication 안에 들어 갈 수 있는 2개의 타입이 있음.
    *  UserDetails/OAuth2User 타입
    *  UserDetails : 일반적인 로그인
    *  OAuth2User :구글, 페이스북 등 소셜로그인
    * */
    @GetMapping("/test/oauth/login")
    public @ResponseBody String loginOauthTest(Authentication authentication,
                                               @AuthenticationPrincipal OAuth2User oauth) {//DI
        System.out.println("==============/test/oauth/login================");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("principalDetails = " +oAuth2User.getAttributes());
        System.out.println("oauth.getAttributes() = " + oauth.getAttributes()); //두개가 같음

        return "Oauth 세션 정보 확인하기";
    }




    @GetMapping({"", "/"})
    public String index() {
        //머스테치 기본펄터 src/main/resources/
        //뷰리졸버 설정: templates(prepix) .mustache(suffix)
        return "index";
    }

    //일반로그인해도, 소셜로그인해도 다 가능
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principlaDetails : "+ principalDetails.getUsers());
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

    @Secured("ROLE_ADMIN") //권한이 맞아야 접근가능
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") //권한이 맞아야 접근가능
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터정보";
    }
}
