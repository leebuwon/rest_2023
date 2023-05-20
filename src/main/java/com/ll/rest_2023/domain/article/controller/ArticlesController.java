package com.ll.rest_2023.domain.article.controller;

import com.ll.rest_2023.base.rsData.RsData;
import com.ll.rest_2023.domain.article.dto.request.ModifyRequest;
import com.ll.rest_2023.domain.article.dto.request.WriteRequest;
import com.ll.rest_2023.domain.article.dto.response.*;
import com.ll.rest_2023.domain.article.entity.Article;
import com.ll.rest_2023.domain.article.service.ArticleService;
import com.ll.rest_2023.domain.member.entity.Member;
import com.ll.rest_2023.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/articles", produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1ArticlesController", description = "게시물 CRUD 컨트롤러")
@Slf4j
public class ArticlesController {
    private final ArticleService articleService;
    private final MemberService memberService;

    @GetMapping
    @Operation(summary = "게시글 전체 조회")
    public RsData<ArticlesResponse> articles() {
        List<Article> articles = articleService.findAll();

        return RsData.of(
                "S-1",
                "성공",
                new ArticlesResponse(articles)
        );
    }

    @GetMapping("{id}")
    @Operation(summary = "단건조회")
    public RsData<ArticleResponse> article(@PathVariable Long id){
        return articleService.findById(id).map(article -> RsData.of("S-1", "성공", new ArticleResponse(article)))
                .orElseGet(() -> RsData.of("F-1", "%d번 게시물은 존재하지 않습니다.".formatted(id), null));
    }

    @PostMapping
    @Operation(summary = "등록", security = @SecurityRequirement(name = "bearerAuth"))
    public RsData<WriteResponse> write(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody WriteRequest writeRequest
    ) {
        Member member = memberService.findByUsername(user.getUsername()).orElseThrow();
        RsData<Article> writeRs = articleService.write(member, writeRequest.getSubject(), writeRequest.getContent());

        if (writeRs.isFail()) return (RsData) writeRs;

        return RsData.of(
                writeRs.getResultCode(),
                writeRs.getMsg(),
                new WriteResponse(writeRs.getData())
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "수정", security = @SecurityRequirement(name = "bearerAuth"))
    public RsData<ModifyResponse> modify(@PathVariable Long id, @AuthenticationPrincipal User user,
                                        @Valid @RequestBody ModifyRequest modifyRequest){
        Member member = memberService.findByUsername(user.getUsername()).orElseThrow();

        Optional<Article> modifyArticle = articleService.findById(id);
        log.info("modifyArticle.get() = {}", modifyArticle.get());

        if (modifyArticle.isEmpty()) {
            return RsData.of("F-1", "%d번 게시물이 존재하지 않습니다.".formatted(id), null);
        }

        RsData rsData = articleService.canModify(member, modifyArticle.get());

        if (rsData.isFail()) return rsData;

        RsData<Article> modify = articleService.modify(modifyArticle.get(), modifyRequest.getSubject(), modifyRequest.getContent());

        return RsData.of(modify.getResultCode(), modify.getMsg(), new ModifyResponse(modify.getData()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "삭제", security = @SecurityRequirement(name = "bearerAuth"))
    public RsData<DeleteResponse> delete(@PathVariable Long id, @AuthenticationPrincipal User user){
        Member member = memberService.findByUsername(user.getUsername()).orElseThrow();

        Optional<Article> deleteArticle = articleService.findById(id);

        if (deleteArticle.isEmpty()){
            return RsData.of("F-1", "%d번 게시물이 존재하지 않습니다.".formatted(id), null);
        }

        RsData rsData = articleService.canDelete(member, deleteArticle.get());

        if (rsData.isFail()) return rsData;

        articleService.delete(deleteArticle.get());

        return RsData.of("S-1", "성공", null);
    }
}