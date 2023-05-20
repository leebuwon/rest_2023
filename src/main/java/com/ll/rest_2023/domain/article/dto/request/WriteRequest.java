package com.ll.rest_2023.domain.article.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WriteRequest {
    @NotBlank
    private String subject;
    @NotBlank
    private String content;
}
