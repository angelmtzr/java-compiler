package com.compilers;

import static com.compilers.Lexer.KEYWORDS;

public enum TokenType {
    // Check first if it is a keyword
    KEYWORD(String.join("|", KEYWORDS)),
    IDENTIFIER("[$_a-zA-Z][a-zA-Z0-9$_]*"),
    BINARY("0[bB][01]+"),
    HEXADECIMAL("0[xX][0-9A-Fa-f]+"),
    OCTAL("0[0-7]+"),
    INTEGER("0|([1-9][0-9]*)"),
    FLOAT("(([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([eE][-+]?[0-9]+)?[FfDd]?)"), // Allows many zeros before
    DELIMITER("[;:,{\\[\\]()}]"),
    OPERATOR("[+\\-*/%|&!<>=^~]"),
    // Escape sequences not allowed yet
    STRING("\"[^\"]+\""), // No empty strings or special character \" allowed
    CHAR("'.'"), // No empty chars or special character \' allowed
    ERROR(null);

    public final String regex;

    TokenType(String regex) {
        this.regex = regex;
    }
}
