package com.diffchecker.java_fundamentals.object_oriented_prog;

public class oop_main {
  public static void main(String args[]) {
    // this is creating an instance (or an object) of a the Student class
    Student ben = new Student();
    ben.Eating("burger");
    ben.Drinking();
    ben.Running();

    // now how do we use the state?
    // By creating Getters and Setters

    // Setters
    String name = ben.name = "Benjamin Button";
    Integer age = ben.age = 12;
    String color = ben.color = "blue";
    char gender = ben.sex = 'm';

    // Getters
    System.out.println("Name: " + name);
    System.out.println("Age: " + age);
    System.out.println("Color: " + color);
    System.out.println("Gender: " + gender);

    Greeting("John");
  }

  // this is a method: its asking for a parameter called "name"
  public static void Greeting(String name) {
    System.out.println("Hello, " + name);
  }

}
