package com.springsecurity.session.config;

import com.springsecurity.session.config.auth.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


//1.코드받기(인증) 2.액세스토큰(권한) 3.사용자프로필 정보를 가져와서 4.그 정보를 토대로 회원가입을 자동으로 진행시키기도 함
//4-2(이메일,전화번호,이름,아이디) 쇼핑몰 ->(집주소),백화점몰->(고객의 등급 vip or 일반)
@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true) //secured 어노테이션 활성.preAuthorize,postAuthorize 어노테이션 활성
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final PrincipalOauth2UserService principalOauth2UserService;

    //해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()//인증만 되면 들어갈 수 있는 주소
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login")//login주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌.
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm")//구글로그인이 완료된 뒤의 후처리가 필요함.
                //TIp. 코드X, (액세스토큰+사용자프로필정보를 한번에 받음)
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
    }
}
