package example;

import java.util.Scanner;

/**
 * Description of the functionality.
 *
 * @author wangp
 * @since 2020-03-21
 */
public class Bottle {

    public static int accumulator;

    public static void calculateBottles(int n) {
        if (n >= 3) {
            accumulator += n / 3;
            calculateBottles(n % 3 + n / 3);
        } else if (n == 2) {
            accumulator += 1;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int n = in.nextInt();
            calculateBottles(n);
            System.out.println(accumulator);
            accumulator = 0;
        }
    }
}
