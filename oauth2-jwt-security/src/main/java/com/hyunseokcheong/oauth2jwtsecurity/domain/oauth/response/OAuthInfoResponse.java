package com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.response;

import com.hyunseokcheong.oauth2jwtsecurity.domain.member.Member;

public interface OAuthInfoResponse {
    
    String getEmail();
    
    Member.OAuthProvider getOAuthProvider();
}
