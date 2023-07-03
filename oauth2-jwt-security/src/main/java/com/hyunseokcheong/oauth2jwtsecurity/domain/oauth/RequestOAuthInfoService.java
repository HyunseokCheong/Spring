package com.hyunseokcheong.oauth2jwtsecurity.domain.oauth;

import com.hyunseokcheong.oauth2jwtsecurity.domain.member.Member;
import com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.client.OAuthClient;
import com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.request.OAuthLoginRequest;
import com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.response.OAuthInfoResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestOAuthInfoService {
    private final Map<Member.OAuthProvider, OAuthClient> clients;
    
    public RequestOAuthInfoService(List<OAuthClient> clients) {
        this.clients = clients.stream().collect(
                Collectors.toUnmodifiableMap(OAuthClient::oAuthProvider, Function.identity())
        );
    }
    
    public OAuthInfoResponse request(OAuthLoginRequest oAuthLoginRequest) {
        OAuthClient client = clients.get(oAuthLoginRequest.oAuthProvider());
        String accessToken = client.requestAccessToken(oAuthLoginRequest);
        return client.requestOauthInfo(accessToken);
    }
}
