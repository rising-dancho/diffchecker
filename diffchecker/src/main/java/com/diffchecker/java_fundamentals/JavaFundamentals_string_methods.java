package com.diffchecker.java_fundamentals;

public class JavaFundamentals_string_methods {
  public static void main(String[] args) {
    // Strings are classes
    // We can create "objects" of Strings. eg. String l = "ligma";
    // We can then use then use its methods ligma.concat("ballz");

    //----> STRING METHODS <----

    // CONCAT
    String l = "ligma";
    System.out.println(l.concat("ballz"));
    // output: ligmaballz

    // think of Classes as recipes
    // "Objects" are the outputs of the Classes/recipes.
    // eg. Recipe (Class) => Output: Fried Rice (Object)
    // Scout scout1 = new Scout("Erwin");
    // Scout scout2 = new Scout("Levi");

    // CONTAINS
    String assCheeks = "assCheeks";
    System.out.println(assCheeks.contains("ass"));
    // output: true

    // INDEX OF
    // in java checking indexes always starts with 0
    // eg. "Hello" => (indexes) 0 1 2 3 4
    String abc = "Hello";
    System.out.println(abc.indexOf("o"));
    // output: 4

    // CHAR AT
    String cba = "Werld";
    System.out.println(cba.charAt(2));
    // output: r

    // REPLACE
    // example.replace(TARGET,REPLACEMENT);
    String example = "Hello World";
    System.out.println(example.replace("Wor", "Bo"));
    // output: Hello Bold

    // TO UPPER CASE, TO LOWER CASE
    String akiraNakai = "rauh welt";
    System.out.println(akiraNakai.toUpperCase());
    // output: RAUH WELT
    System.out.println(akiraNakai.toLowerCase());
    // output: rauh welt
  }
}
