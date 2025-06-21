package com.diffchecker.java_fundamentals;

import java.util.Scanner;

public class JavaFundamentals_get_prime_numbers {
  public static void main(String[] args) {

    // Exercise 5:
    // Write a java program to display prime numbers from 1 to n ( entered by user)

    System.out.print("Enter a number ");
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();

    // int n = 10;

    // part 2 - Algorithm of prime number
    String primeNumbers = "";
    String primes = "2, 3, 5, 7, 11, 13, 17, 19, 23, ...";
    System.out.println("Example of prime numbers \n" + primes);

    // n = the number the user entered. its a constant,
    // n does not change it's value inside the loop
    for (int i = 1; i <= n; i++) {
      // outer loops count upward to n
      // System.out.println("i " + i);

      // this would reset the value of the counter to 0 at every new value of i
      int counter = 0;

      for (int num = i; num >= 1; num--) {
        // num = i = i++
        // inner loop counts down
        System.out.println("num " + num);
        System.out.println("i " + i);

        if (i % num == 0) {
          counter++;
          System.out.println("counter " + counter);
        }

      }
      if (counter == 2) {
        // Appending the prime numbers to the string
        primeNumbers = primeNumbers + i + " ";
      }
      System.out.println();
    }
    scanner.close();

    System.out.print("Prime numbers from 1 to " + n + " are: " + primeNumbers);
  }

}
// Example of prime numbers
// 2, 3, 5, 7, 11, 13, 17, 19, 23, ...
