package com.hyunseokcheong.oauth2jwtsecurity.domain.oauth;

import com.hyunseokcheong.oauth2jwtsecurity.domain.jwt.AuthTokens;
import com.hyunseokcheong.oauth2jwtsecurity.domain.jwt.AuthTokensGenerator;
import com.hyunseokcheong.oauth2jwtsecurity.domain.member.Member;
import com.hyunseokcheong.oauth2jwtsecurity.domain.member.MemberRepository;
import com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.request.OAuthLoginRequest;
import com.hyunseokcheong.oauth2jwtsecurity.domain.oauth.response.OAuthInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;
    
    public AuthTokens login(OAuthLoginRequest oAuthLoginRequest) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(oAuthLoginRequest);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }
    
    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(Member::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }
    
    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
                .email(oAuthInfoResponse.getEmail())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();
        
        return memberRepository.save(member).getId();
    }
}
