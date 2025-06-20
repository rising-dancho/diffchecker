package com.diffchecker.java_fundamentals;

// import java.util.Scanner;

public class JavaFundamentals_get_prime_numbers {
  public static void main(String[] args) {

    // Exercise 5:
    // Write a java program to display prime numbers from 1 to n ( entered by user)

    // System.out.print("Enter a number ");
    // Scanner scanner = new Scanner(System.in);
    // int n = scanner.nextInt();

    int n = 2;

    // n = the number the user entered. its a constant,
    // n does not change it's value inside the loop
    for (int i = 1; i <= n; i++) {
      // outer loops count upward to n
      // System.out.println("i " + i);
      for (int num = i; num >= 1; num--) {
        // num = i = i++
        // inner loop counts down
        System.out.println("num " + num);
      }
    }
    // scanner.close();
  }

  // Tracing The Inner And Outer Loop
  /*
   * n = 2 (CONSTANT VALUE)
   * 
   * Step Code Description
   * Initialization int i=1 Variable i is created and initialized to 1 (step 1)
   * 
   * Outer Loop Test #1 i=1 True, we can enter the loop (step 2)
   * i<=2
   * 
   * Body {...} Execute the Inner Loop because i = (1) less than or equal to 2 is
   * true
   * 
   * Inner Loop Test #1 num = 1, num = i => 1 >= 1 True; execute the code inside
   * the inner loop
   * num >= 1
   * 
   * Update Inner Loop num-- Decrement num, which becomes 0;
   * 
   * Update Outer Loop i++ Increment i, which becomes 2
   * 
   * Outer Loop Test #2 i=2 True, as long as still true we enter the loop (step 3)
   * i<=2
   * 
   * Body {...} Execute the Inner Loop because i = (2) equal to 2 is true
   * 
   * Inner Loop Test #2 num = 2, num = i => 2 >= 1 True; execute the code inside
   * the inner loop
   * num >= 1
   * 
   * Update Inner Loop num-- Decrement num, which becomes 1;
   * Update Outer Loop i++ Increment i, which becomes 3
   * 
   * Outer Loop Test #3 i=3 False, we do not enter the loop (step 4)
   * i<=2
   * 
   * num => 1,2,1
   */
}
