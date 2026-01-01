package com.redfox.cuibuilder;

import java.util.Map;

public class Test {
    public static void main(String[] args) {
        new CUIBuilder("Test", Map.of(
                new Command("test", "test command"), System.out::println
        ), System.out::println);
    }
}
