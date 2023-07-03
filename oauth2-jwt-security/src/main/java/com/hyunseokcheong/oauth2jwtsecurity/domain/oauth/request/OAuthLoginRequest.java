package com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.request;

import com.hyunseokcheong.oauth2jwtsecurity.domain.member.Member;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginRequest {
    
    Member.OAuthProvider oAuthProvider();
    
    MultiValueMap<String, String> makeBody();
}
