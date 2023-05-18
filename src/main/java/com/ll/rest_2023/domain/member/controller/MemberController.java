package com.ll.rest_2023.domain.member.controller;

import com.ll.rest_2023.base.rsData.RsData;
import com.ll.rest_2023.domain.member.dto.LoginRequestDto;
import com.ll.rest_2023.domain.member.entity.Member;
import com.ll.rest_2023.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/member", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class MemberController {

    @AllArgsConstructor
    @Getter
    public static class LoginResponse {
        private final String accessToken;
    }

    private final MemberService memberService;

    @PostMapping("/login")
    public RsData<LoginResponse> login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse resp) {
        String accessToken = memberService.genAccessToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());

        return RsData.of(
                "S-1",
                "엑세스토큰이 생성되었습니다.",
                new LoginResponse(accessToken)
        );
    }
}
