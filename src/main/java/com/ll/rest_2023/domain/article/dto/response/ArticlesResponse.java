package com.ll.rest_2023.domain.article.dto.response;

import com.ll.rest_2023.domain.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ArticlesResponse {
    private final List<Article> articles;
}
