package com.compilers;

import static com.compilers.TokenType.ERROR;
import static com.compilers.TokenType.FLOAT;

public class Word {
    private QuoteType currentQuotesOpen;
    private String val;

    public Word() {
        this("");
    }

    public Word(String str) {
        val = str;
        currentQuotesOpen = null;
    }

    public Word(char c) {
        this(String.valueOf(c));
    }

    public String getVal() {
        return val;
    }

    public void append(char c) {
        val = val + c;
    }

    public void clear() {
        val = "";
    }

    public void openQuotes(QuoteType quoteType) {
        val = String.valueOf(quoteType.val);
        currentQuotesOpen = quoteType;
    }

    public void closeQuotes(QuoteType quoteType) {
        val = val + quoteType.val;
        currentQuotesOpen = null;
    }

    public TokenType getTokenType() {
        for (var tokenType : TokenType.values()) {
            var regex = tokenType.regex;
            if (regex == null)
                continue;
            if (val.matches(regex))
                return tokenType;
        }
        return ERROR;
    }

    public boolean isNotEmpty() {
        return !val.isEmpty();
    }

    public boolean isValid(TokenType tokenType) {
        return val.matches(tokenType.regex);
    }

    public boolean hasOpenQuotes(QuoteType quoteType) {
        return currentQuotesOpen == quoteType;
    }

    public boolean hasOpenAnyQuotes() {
        return currentQuotesOpen != null;
    }

    public boolean isPotentialFloat() {
        // If current word appending a sign and number is a FLOAT is potential FLOAT
        return new Word(val + "+1").getTokenType() == FLOAT;
    }

    public void reset() {
        val = "";
        currentQuotesOpen = null;
    }
}