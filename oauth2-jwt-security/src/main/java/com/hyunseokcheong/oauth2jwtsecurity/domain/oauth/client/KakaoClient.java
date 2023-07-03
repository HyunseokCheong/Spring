package com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.client;

import com.hyunseokcheong.oauth2jwtsecurity.domain.member.Member;
import com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.request.OAuthLoginRequest;
import com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.response.KakaoInfoResponse;
import com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.response.OAuthInfoResponse;
import com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.token.KakaoTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class KakaoClient implements OAuthClient {
    
    @Value("${oauth.kakao.grant-type}")
    private String grantType;
    
    @Value("${oauth.kakao.url.auth}")
    private String authUrl;
    
    @Value("${oauth.kakao.url.api}")
    private String apiUrl;
    
    @Value("${oauth.kakao.client-id}")
    private String clientId;
    
    private final RestTemplate restTemplate;
    
    @Override
    public Member.OAuthProvider oAuthProvider() {
        return Member.OAuthProvider.KAKAO;
    }
    
    
    @Override
    public String requestAccessToken(OAuthLoginRequest oAuthLoginRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        MultiValueMap<String, String> body = oAuthLoginRequest.makeBody();
        body.add("grant_type", grantType);
        body.add("client_id", clientId);
        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        
        KakaoTokens response = restTemplate.postForObject(authUrl, request, KakaoTokens.class);
        
        assert response != null;
        return response.getAccessToken();
    }
    
    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\"]");
        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        
        KakaoInfoResponse response = restTemplate.postForObject(apiUrl, request, KakaoInfoResponse.class);
        
        assert response != null;
        return response;
    }
}
