package com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.request;

import com.hyunseokcheong.oauth2jwtsecurity.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@NoArgsConstructor
public class KakaoLoginRequest implements OAuthLoginRequest {
    
    private String grantType;
    
    private String clientId;
    
    private String redirectUri;
    
    private String code;
    
    @Override
    public Member.OAuthProvider oAuthProvider() {
        return Member.OAuthProvider.KAKAO;
    }
    
    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("grant_type", grantType);
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        return body;
    }
}
