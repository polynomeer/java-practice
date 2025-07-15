package com.polynomeer.encoding;

import java.nio.charset.StandardCharsets;

public class UTFEncoding {

    public static void main(String[] args) {
        testChar("A");     // U+0041
        testChar("가");     // U+AC00
        testChar("𐍈");     // U+10348 (Surrogate pair)
    }

    private static void testChar(String text) {
        System.out.println("> 문자: " + text);
        System.out.println(" - 코드 포인트: U+" + Integer.toHexString(text.codePointAt(0)).toUpperCase());

        byte[] utf8 = text.getBytes(StandardCharsets.UTF_8);
        byte[] utf16 = text.getBytes(StandardCharsets.UTF_16);

        byte[] utf16le = text.getBytes(StandardCharsets.UTF_16LE);
        byte[] utf16be = text.getBytes(StandardCharsets.UTF_16BE);

        System.out.println(" - UTF-8:     " + toHex(utf8));
        System.out.println(" - UTF-16:    " + toHex(utf16) + " (BOM 포함)");
        System.out.println(" - UTF-16LE:  " + toHex(utf16le));
        System.out.println(" - UTF-16BE:  " + toHex(utf16be));
        System.out.println();
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().trim();
    }
}
