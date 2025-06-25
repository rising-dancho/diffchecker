package com.diffchecker.java_fundamentals;

public class JavaFundamentals_multidimentional_arrays {
  public static void main(String[] args) {
    // MULTIDIMENTIONAL ARRAYS
    // prerequisite:
    // - study "Matrices" Linear Algebra
    // - https://www.youtube.com/watch?v=yRwQ7A6jVLk
    // - know the meaning of "indices" in Java

    int arr[][] = new int[][] {
        { 2, 7, 8 },
        { 3, 6, 1 },
        { 7, 4, 2 }
    };
    // What is the "Order" of this Matrix?
    // 3 x 3 => 3 Rows x 3 Columns

    // This is how to print out a 2D array
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr.length; j++) {
        // This is how to print out a value inside a 2D array
        System.out.println(arr[i][j]);
      }
    }

  }
}
