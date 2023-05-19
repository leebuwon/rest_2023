package com.ll.rest_2023.base.initData;

import com.ll.rest_2023.domain.article.service.ArticleService;
import com.ll.rest_2023.domain.member.entity.Member;
import com.ll.rest_2023.domain.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile({"dev", "test"})
public class NotProd {
    @Bean
    CommandLineRunner initData(MemberService memberService, ArticleService articleService, PasswordEncoder passwordEncoder) {
        String password = passwordEncoder.encode("1234");
        return args -> {
            Member admin = memberService.join("admin", password, "admin@test.com");
            Member member1 = memberService.join("user1", password, "user1@test.com");
            Member member2 = memberService.join("user2", password, "user2@test.com");

            articleService.write(admin, "admin 제목1", "admin 내용1");
            articleService.write(admin, "admin 제목2", "admin 내용2");
            articleService.write(admin, "admin 제목3", "admin 내용3");

            articleService.write(member1, "user1 제목4", "user1 내용4");
            articleService.write(member1, "user1 제목5", "user1 내용5");
            articleService.write(member1, "user1 제목6", "user1 내용6");

            articleService.write(member2, "user2 제목7", "user2 내용7");
            articleService.write(member2, "user2 제목8", "user2 내용8");
            articleService.write(member2, "user2 제목9", "user2 내용9");

        };
    }
}