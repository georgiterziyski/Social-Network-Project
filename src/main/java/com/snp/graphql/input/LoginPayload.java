package com.snp.graphql.input;

import com.snp.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginPayload {
    private final String token;
    private final User user;
}
