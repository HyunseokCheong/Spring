package com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.request;

import com.hyunseokcheong.oauth2jwtsecurity.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@NoArgsConstructor
public class KakaoLoginRequest implements OAuthLoginRequest {
    
    private String authorizationCode;
    
    @Override
    public Member.OAuthProvider oAuthProvider() {
        return Member.OAuthProvider.KAKAO;
    }
    
    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        return body;
    }
}
