package com.springsecurity.session.config.auth;



import com.springsecurity.session.model.Users;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


// 시큐리티가 /login을 주소요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 시큐리티만의 session을 만들어 준다.(Security ContextHolder)
// 오브젝트 타입 = > Authentication 타입의 객체
// Authentication 안에 User정보가 있어야 함.
// User오브젝트타입 = > UserDetails 타입 객체

// Security Session = > Authentication = > UserDetails(PrincipalDetails)
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private Users users;// 콤포지션
    private Map<String, Object> attributes;

    //일반로그인
    public PrincipalDetails(Users users) {
        this.users = users;
    }
    //Oauth로그인
    public PrincipalDetails(Users users,Map<String,Object> attributes) {
        this.users = users;
        this.attributes = attributes;
    }



    @Override//해당 유저의 권한을 리턴
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return users.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
    }

    //계정 만료됐나?
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }//만료 안됨

    //계정 잠겼나?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }//안 잠김

    //계정 비밀번호 너무 오래쓴거아님?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }//아님

    //계정 활성화 되어있나?
    @Override
    public boolean isEnabled() {
        // 우리사이트에서 1년간 회원이 로그인을 안하면 휴면 계정으로 하기로 함.
        // 현재시간 - 로그인 시간 => 1년 초과하면 return false
        return true;//네
    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
