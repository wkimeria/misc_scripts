package com.kimeria;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ErastothenesSieve {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the upper limit for prime number search.");
        int upperLimit = in.nextInt();

        long start = System.currentTimeMillis();

        ArrayList<Integer> primes = getPrimeNumbers(upperLimit);

        // 1 is a prime number
        primes.add(0, 1);

        System.out.println("Number of primes in first " + upperLimit + " numbers is " + primes.size());

        //If less than 50 primes print to screen
        if(primes.size() < 50){
            System.out.println("Prime Numbers are:");
            System.out.println(primes);
        }
        try {
            FileWriter myWriter = new FileWriter("primes.txt");

            for( int i=0;i< primes.size();i++){
                if(primes.get(i) == 0){
                    continue;
                }
                myWriter.write(primes.get(i) + System.lineSeparator());

                if(i % 10000 == 0){
                    myWriter.flush();
                }
            }

            myWriter.flush();
            myWriter.close();
            System.out.println("All prime numbers written to 'primes.txt'");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        long time = (System.currentTimeMillis() - start) / 1000;
        System.out.println("Runtime in Seconds = " + time);
    }

    /**
     * Given a list of numbers, narrow down the list to primes
     * Numbers that are not prime numbers will be replaced with 0
     *
     * @param  primeCandidates      List of ints
     * @return                      List of ints
     */
    public static int[] prunePrimeCandidates(int[] primeCandidates) {
        for( int i=2;i< primeCandidates.length;i++){
            if(primeCandidates[i-2] == 0){
                continue;
            }

            int multiplier = 2;
            int c = i * multiplier;

            innerLoop:
            while (c <= primeCandidates.length * 2){
                int idx = (c-2);
                if(idx > primeCandidates.length-1){
                    break innerLoop;
                }

                primeCandidates[idx] = 0;
                multiplier += 1;
                c = i * multiplier;
            }
        }

        return primeCandidates;
    }

    /**
     * Given an upper limit, return an arrayList of prime numbers
     * from 1 to the upper limit
     *
     * @param  upperLimit   The upper limit
     * @return              An ArrayList of prime numbers
     */
    public static ArrayList<Integer> getPrimeNumbers(int upperLimit){

        int[] primeCandidates = new int[upperLimit-1];
        for( int i=2;i<= upperLimit;i++){
            primeCandidates[i-2] = i;
        }

        int[] prunedPrimes = prunePrimeCandidates(primeCandidates);
        ArrayList<Integer> primes = new ArrayList<>();

        for( int i=0;i< prunedPrimes.length;i++){
            int val = prunedPrimes[i];
            if(val== 0){
                continue;
            }
            primes.add(val);
        }

        return primes;
    }
}