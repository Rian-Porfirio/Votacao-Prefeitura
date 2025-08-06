package br.com.rianporfirio.sistemavotacao.utils;

public class NameUtils {
    public static String removePontuationAndSpaces(String name) {
        return name.replaceAll("\\p{Punct}", " ")
                   .replaceAll("\\s+", "_");
    }
}
