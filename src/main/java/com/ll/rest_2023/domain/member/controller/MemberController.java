package com.ll.rest_2023.domain.member.controller;

import com.ll.rest_2023.domain.member.dto.LoginRequestDto;
import com.ll.rest_2023.domain.member.entity.Member;
import com.ll.rest_2023.domain.member.service.MemberService;
import jakarta.validation.Valid;
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

    private final MemberService memberService;

    @PostMapping("/login")
    public Member login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return memberService.findByUsername(loginRequestDto.getUsername()).orElse(null);
    }
}
