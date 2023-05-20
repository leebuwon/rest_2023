package com.ll.rest_2023.domain.article.dto.response;

import com.ll.rest_2023.domain.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WriteResponse {
    private final Article article;
}
