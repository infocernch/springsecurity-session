package com.springsecurity.jwt.config.auth;



import com.springsecurity.jwt.model.Users;
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

    public PrincipalDetails(Users users) {
        this.users = users;
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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 우리사이트에서 1년간 회원이 로그인을 안하면 휴면 계정으로 하기로 함.
        // 현재시간 - 로그인 시간 => 1년 초과하면 return false
        return true;
    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }
}
