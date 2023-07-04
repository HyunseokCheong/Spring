package com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunseokcheong.oauth2jwtsecurity.domain.member.Member;
import com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.request.OAuthLoginRequest;
import com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.response.KakaoInfoResponse;
import com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.response.OAuthInfoResponse;
import com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.token.KakaoTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;


@Component
@RequiredArgsConstructor
public class KakaoClient implements OAuthClient {
    
    @Value("${oauth.kakao.url.auth}")
    private String authUrl;
    
    @Value("${oauth.kakao.url.api}")
    private String apiUrl;
    
    @Override
    public Member.OAuthProvider oAuthProvider() {
        return Member.OAuthProvider.KAKAO;
    }
    
    @Override
    public String requestAccessToken(OAuthLoginRequest oAuthLoginRequest) {
        return Objects.requireNonNull(WebClient.builder()
                .baseUrl(authUrl)
                .build()
                .post()
                .uri("/oauth/token")
                .bodyValue(oAuthLoginRequest.makeBody())
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(KakaoTokens.class)
                .block()).getAccessToken();
    }
    
    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        // body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\"]");
        
        // request
        return WebClient.builder()
                .baseUrl(apiUrl)
                .build()
                .post()
                .uri("/v2/user/me")
                .body(BodyInserters.fromFormData(body))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8") //요청 헤더
                .retrieve()
                .bodyToMono(KakaoInfoResponse.class)
                .block();
    }
}
