package com.springsecurity.jwt.config.auth.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest = " + userRequest.getClientRegistration());//registrationId로 어떤 Oauth로 로그인 했는지 확인 가능
        System.out.println("userRequest.get = " + userRequest.getAccessToken().getTokenValue());
        // 구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인완료 -> code리턴받음(oauth-client라이브러리가 받아줌)->
        //AccessToken요청 .. 여기까지가 userRequest정보임. -> loadUser함수 호출 --> 회원프로필 정보를 받아옴. (구글로부터)

        System.out.println("super.loadUser(userRequest).getAttributes() = " + super.loadUser(userRequest).getAttributes());
        /*
        * super.loadUser(userRequest).getAttributes() =
        * {sub=101921075351750406179, (PK같은 느낌)
        * name=우병걸,
        * given_name=병걸
        * , family_name=우,
        * picture=https://lh3.googleusercontent.com/a/ACg8ocJnnKnxxPeAsAIoK_0Ch1mjyLGayXMfczI205AFeVFH=s96-c,
        * email=wbg030281@gmail.com,
        * email_verified=true,
        * locale=ko}
        *
        * username = sub= google_101921075351750406179 (중복될일없음)
        * password = 암호화(겟인데어) (우리서버만 아는) null만 아니면 아무거나해도 상관없음
        * email = email=wbg030281@gmail.com
        * role = ROLE_USER
        * provider = "google"
        * providerId = sub=101921075351750406179
        * 즉, google에서 받아온 정보들로만 해서 회원가입을 진행시킴.
        * super.loadUser(userRequest); 이걸로 강제 회원가입 시킴.
        * */

        OAuth2User oAuth2User = super.loadUser(userRequest);


        return super.loadUser(userRequest);
    }
}
