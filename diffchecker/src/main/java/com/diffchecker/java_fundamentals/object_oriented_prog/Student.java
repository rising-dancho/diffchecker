package com.diffchecker.java_fundamentals.object_oriented_prog;

public class Student {
  // Classes - are blueprints, it defines states and behaviors.
  // Objects - are the living instances of the Class.

  // States
  String name;
  Integer age;
  String color;
  char sex; // f or m

  // Behaviors
  // (methods) are block of codes which only runs when called
  public void Eating(String food) {
    System.out.println("Eating "+ food);
  }

  public void Drinking(){
    System.out.println("Drinking");
  }

  public void Running(){
    System.out.println("Running");
  }

}
