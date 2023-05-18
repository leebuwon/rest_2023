package com.ll.rest_2023.domain.member.dto.response;

import com.ll.rest_2023.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MeResponse {
    private final Member member;
}
