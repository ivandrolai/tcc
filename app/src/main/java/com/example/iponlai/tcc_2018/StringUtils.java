package com.example.iponlai.tcc_2018;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtils {
    private StringUtils() {}

    public static String semAcentos(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static void main(String args[]) throws Exception{
        String value = "� � � _ @";
        System.out.println(StringUtils.semAcentos(value));
    }
}
