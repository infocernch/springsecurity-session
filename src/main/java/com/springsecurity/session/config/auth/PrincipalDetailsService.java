package com.springsecurity.session.config.auth;

import com.springsecurity.session.model.Users;
import com.springsecurity.session.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//시큐리티 설정에서 loginProcessingUrl("/login");
//로그인 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //시큐리티 session(Authentication(UserDetails))
    @Override //받는 파라미터를 form태그의 name과 같이 맞춰야 매핑이 됌.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username = " + username);
        Users usersEntity = userRepository.findByUsername(username);
        System.out.println("usersEntity = " + usersEntity);
        if (username != null) {
            return new PrincipalDetails(usersEntity);
        }
        return null;
    }
}
