package com.task.minitweet.domains.dtos.auth;

import com.task.minitweet.domains.models.Token;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDto {
    private String token;

    public TokenDto(Token token) {
        this.token = token.getContent();
    }
}
