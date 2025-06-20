package com.diffchecker.backup;

import java.util.Scanner;

public class getting_prime {

        public static void main(String[] args) {

                // Exercise 5:
                // Write a java program to display prime numbers from 1 to n ( entered by user)

                // part 1 - Getting number from user
                Scanner scanner = new Scanner(System.in);
                System.out.print("Please enter a number");
                int n = scanner.nextInt();

                int num = 0;

                // part 2 - Algorithm of prime number
                String primeNumbers = "";

                for (int i = 1; i <= n; i++) {

                        int counter = 0;

                        for (num = i; num >= 1; num--) {

                                if (i % num == 0) {
                                        counter++;
                                }

                        }

                        // 7: 1, 7 counter: 2
                        // 8: 1,2,4,8 counter: 4

                        if (counter == 2) {
                                // Appending the prime numbers to the string
                                primeNumbers = primeNumbers + i + " ";
                        }

                }

                System.out.print("Prime numbers from 1 to n are: " + primeNumbers);

        }
}
