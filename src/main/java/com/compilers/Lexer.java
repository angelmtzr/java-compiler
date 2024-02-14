package com.compilers;

import java.util.Vector;

public final class Lexer {
    public static final String[] KEYWORDS = {
            "if",
            "else",
            "for",
            "while",
            "switch",
            "case",
            "return",
            "int",
            "float",
            "void",
            "char",
            "string",
            "boolean",
            "true",
            "false",
            "continue",
            "default",
            "break",
            "do",
            "null",
            "final",
            "short",
            "byte",
            "long"

    };

    public static void main(String[] args) {
        var input = "\"hola\" \"hola\"";

        System.out.println(tokenize(input));
    }

    public static Vector<Token> tokenize(String text) {
        var tokens = new Vector<Token>();

        text = text.replace("\t", "\s\s"); // Substitute tabs with whitespaces

        var lines = text.split("\r\n|\n|\n\n"); // All possible line separators (depends on OS)


        var lineNum = 1;
        var str = new StringBuilder();
        var doubleQuotesOpen = false;
        var singleQuotesOpen = false;
        for (var line : lines) {
            for (var c : line.toCharArray()) {
                if (isDoubleQuote(c)) {
                    // Double quotes can be inside single quotes
                    if (singleQuotesOpen) {
                        str.append(c);
                        continue;
                    }
                    // Incoming closing quotes
                    if (doubleQuotesOpen) {
                        str.append(c); // append closing quotes
                        tokens.add(new Token(lineNum, identifyToken(str.toString()), str.toString()));
                        str = new StringBuilder(); // reset string
                        doubleQuotesOpen = false; // set flag to closed quotes
                        continue; // next character
                    }
                    // Incoming opening quotes
                    //  First classify previous string (if any)
                    if (!str.isEmpty()) {
                        tokens.add(new Token(lineNum, identifyToken(str.toString()), str.toString()));
                        str = new StringBuilder();
                    }
                    str.append(c);
                    doubleQuotesOpen = true;
                    continue;
                }
                if (isSingleQuote(c)) {
                    // Single quotes can be inside double quotes
                    if (doubleQuotesOpen) {
                        str.append(c);
                        continue;
                    }
                    // Incoming closing quotes
                    if (singleQuotesOpen) {
                        str.append(c); // append closing quotes
                        tokens.add(new Token(lineNum, identifyToken(str.toString()), str.toString()));
                        str = new StringBuilder(); // reset string
                        singleQuotesOpen = false; // set flag to closed quotes
                        continue; // next character
                    }
                    // Incoming opening double quotes
                    //  First classify previous string (if any)
                    if (!str.isEmpty()) {
                        tokens.add(new Token(lineNum, identifyToken(str.toString()), str.toString()));
                        str = new StringBuilder();
                    }
                    str.append(c);
                    singleQuotesOpen = true;
                    continue;
                }
                if (isDelimiter(c) || isOperator(c) || isWhitespace(c) || isHash(c)) {
                    // Append normally if quotes open
                    if (doubleQuotesOpen || singleQuotesOpen) {
                        str.append(c);
                        continue;
                    }
                    // Scientific notation
                    // If string appending a sign and number is a FLOAT then the sign is for FLOAT
                    if ((c == '+' || c == '-') && identifyToken(str + "+1") == TokenType.FLOAT) {
                        str.append(c);
                        continue;
                    }
                    // If hash (comment indicator) break to next line
                    if (isHash(c)) {
                        break;
                    }
                    // Classify previous string (if any)
                    if (!str.isEmpty()) {
                        tokens.add(new Token(lineNum, identifyToken(str.toString()), str.toString()));
                    }
                    // Classify word separator (except spaces)
                    if (!isWhitespace(c)) {
                        tokens.add(new Token(lineNum, identifyToken(String.valueOf(c)), String.valueOf(c)));
                    }
                    str = new StringBuilder(); // Reset string
                    continue;
                }
                // Any other character is appended normally
                str.append(c);
            }
            // Classify word left when line ends (if any)
            if (!str.isEmpty())
                tokens.add(new Token(lineNum, identifyToken(str.toString()), str.toString()));
            // New line, new string
            str = new StringBuilder();
            doubleQuotesOpen = false;
            singleQuotesOpen = false;
            lineNum++;
        }
        return tokens;
    }

    private static TokenType identifyToken(String str) {
        for (var tokenType : TokenType.values()) {
            if (tokenType.regex == null)
                continue;

            var regex = tokenType.regex;
            if (str.matches(regex)) {
                return tokenType;
            }
        }
        return TokenType.ERROR;
    }

    private static boolean isHash(char c) {
        return c == '#';
    }

    private static boolean isSingleQuote(char c) {
        return c == '\'';
    }

    private static boolean isDoubleQuote(char c) {
        return c == '"';
    }

    private static boolean isWhitespace(char c) {
        return c == ' ';
    }

    private static boolean isOperator(char c) {
        return String.valueOf(c).matches(TokenType.OPERATOR.regex);
    }

    private static boolean isDelimiter(char c) {
        return String.valueOf(c).matches(TokenType.DELIMITER.regex);
    }
}
