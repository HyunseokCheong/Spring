package com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.client;

import com.hyunseokcheong.oauth2jwtsecurity.domain.member.Member;
import com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.request.OAuthLoginRequest;
import com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.response.OAuthInfoResponse;

public interface OAuthClient {
    
    Member.OAuthProvider oAuthProvider();
    
    String requestAccessToken(OAuthLoginRequest oAuthLoginRequest);
    
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
