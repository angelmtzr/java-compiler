package com.compilers;

public record Token(int line, TokenType type, String value) {
    public boolean isError() {
        return this.type == TokenType.ERROR;
    }
}
