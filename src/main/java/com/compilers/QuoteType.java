package com.compilers;

public enum QuoteType {
    SINGLE('\''), DOUBLE('"');
    final char val;

    QuoteType(char c) {
        val = c;
    }
}
