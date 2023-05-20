package com.ll.rest_2023.domain.article.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ModifyRequest {

    @NotBlank
    private String subject;
    @NotBlank
    private String content;
}
