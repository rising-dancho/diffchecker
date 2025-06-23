package com.diffchecker.java_fundamentals;

public class JavaFundamentals_string_methods {
  public static void main(String[] args) {
    // Strings are classes
    // We can create "objects" of Strings. eg. String l = "ligma";
    // We can then use then use its methods ligma.concat("ballz");

    String l = "ligma";
    System.out.println(l.concat("ballz"));
    // output: ligmaballz

    // think of Classes as recipes
    // "Objects" are the outputs of the Classes/recipes.
    // eg. Recipe (Class) => Output: Fried Rice (Object)
    // Scout scout1 = new Scout("Erwin");
    // Scout scout2 = new Scout("Levi");

    // String methods
    String assCheeks = "assCheeks";
    System.out.println(assCheeks.contains("ass"));
    // output: true

  }
}
