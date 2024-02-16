package com.compilers;

import java.util.Vector;

import static com.compilers.TokenType.*;

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
    private final static Vector<Token> tokens = new Vector<>();
    private static final Word word = new Word();

    public static Vector<Token> tokenize(String str) {
        var lineNum = 1;
        tokens.setSize(0);
        for (var line : str.split("(\r\n)|\n")) {
            for (var c : line.toCharArray()) {
                // HANDLE NON-SPECIAL BEHAVIOR CHARS
                if (isNotSpecialBehaviorChar(c)) {
                    word.append(c);
                    continue;
                }
                // HANDLE SPECIAL BEHAVIOR CHARS
                if (isDoubleQuote(c)) {
                    // Double quotes inside single quotes
                    if (word.hasOpenQuotes(QuoteType.SINGLE)) {
                        word.append(c);
                        continue;
                    }
                    // Opening double quotes case
                    if (!word.hasOpenQuotes(QuoteType.DOUBLE)) {
                        if (word.isNotEmpty()) {
                            tokens.add(new Token(lineNum, word.getTokenType(), word.getVal()));
                        }
                        word.openQuotes(QuoteType.DOUBLE);
                    }
                    // Closing double quotes case
                    else {
                        word.closeQuotes(QuoteType.DOUBLE);
                        tokens.add(
                                new Token(
                                        lineNum,
                                        word.isValid(STRING) ? STRING : ERROR,
                                        word.getVal()
                                )
                        );
                        word.clear();
                    }
                    // Next char
                    continue;
                }
                if (isSingleQuote(c)) {
                    // Single quotes inside double quotes
                    if (word.hasOpenQuotes(QuoteType.DOUBLE)) {
                        word.append(c);
                        continue;
                    }
                    // Opening single quotes case
                    if (!word.hasOpenQuotes(QuoteType.SINGLE)) {
                        if (word.isNotEmpty()) {
                            tokens.add(new Token(lineNum, word.getTokenType(), word.getVal()));
                        }
                        word.openQuotes(QuoteType.SINGLE);
                    }
                    // Closing single quotes case
                    else {
                        word.closeQuotes(QuoteType.SINGLE);
                        tokens.add(
                                new Token(
                                        lineNum,
                                        word.isValid(CHAR) ? CHAR : ERROR,
                                        word.getVal()
                                )
                        );
                        word.clear();
                    }
                    // Next char
                    continue;
                }
                // Special behavior chars inside quotes are non-special
                if (word.hasOpenAnyQuotes()) {
                    word.append(c);
                    continue;
                }
                // Line comment char will break to next line
                if (isLineCommentChar(c)) {
                    break;
                }
                // The "+-" signs could be for scientific notation (e.g. 3.2e+1)
                if (isSign(c) && word.isPotentialFloat()) {
                    word.append(c);
                    continue;
                }
                // Classify word previous to separator char (if not empty)
                if (word.isNotEmpty()) {
                    tokens.add(new Token(lineNum, word.getTokenType(), word.getVal()));
                }
                // Classify word separator (except spaces and tabs)
                if (isNotWhitespaceOrTab(c)) {
                    var separator = new Word(c);
                    tokens.add(
                            new Token(
                                    lineNum,
                                    separator.getTokenType(),
                                    separator.getVal()
                            )
                    );
                }
                word.clear();
            }
            // Add pending word and create new word when line ends
            if (word.isNotEmpty()) {
                tokens.add(new Token(lineNum, word.getTokenType(), word.getVal()));
            }
            word.reset();
            lineNum++;
        }

        return tokens;
    }

    private static boolean isNotSpecialBehaviorChar(char c) {
        return isNotOperator(c) &&
                isNotDelimiter(c) &&
                isNotWhitespaceOrTab(c) &&
                !isSingleQuote(c) &&
                !isDoubleQuote(c) &&
                !isLineCommentChar(c);
    }

    private static boolean isSign(char c) {
        return c == '+' || c == '-';
    }

    private static boolean isLineCommentChar(char c) {
        return c == '#';
    }

    private static boolean isSingleQuote(char c) {
        return c == '\'';
    }

    private static boolean isDoubleQuote(char c) {
        return c == '"';
    }

    private static boolean isNotWhitespaceOrTab(char c) {
        return c != ' ' && c != '\t';
    }

    private static boolean isNotOperator(char c) {
        return !String.valueOf(c).matches(TokenType.OPERATOR.regex);
    }

    private static boolean isNotDelimiter(char c) {
        return !String.valueOf(c).matches(TokenType.DELIMITER.regex);
    }
}
