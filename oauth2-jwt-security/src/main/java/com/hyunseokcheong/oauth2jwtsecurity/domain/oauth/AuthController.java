package com.hyunseokcheong.oauth2jwtsecurity.domain.oauth;

import com.hyunseokcheong.oauth2jwtsecurity.domain.jwt.AuthTokens;
import com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.request.KakaoLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    
    private final OAuthLoginService oAuthLoginService;
    
    @PostMapping("/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginRequest kakaoLoginRequest) {
        return ResponseEntity.ok(oAuthLoginService.login(kakaoLoginRequest));
    }
}
