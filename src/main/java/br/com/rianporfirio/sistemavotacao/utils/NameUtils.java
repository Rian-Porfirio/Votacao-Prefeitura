package br.com.rianporfirio.sistemavotacao.utils;

public class NameUtils {
    public static String removeSpaces(String name) {
        return name.replaceAll("\\s+", "_");
    }
}
