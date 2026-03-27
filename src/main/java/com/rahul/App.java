package com.rahul;

import com.rahul.counter.LineCounter;

/**
 * Console entry point.
 * Usage: java -jar ImageLineCounter.jar <absolute-path-to-image>
 */
public class App {

    public static void main(String[] args) {
        System.exit(run(args));
    }

    // Package-private so tests can invoke this without triggering System.exit()
    static int run(String[] args) {
        if (args.length != 1) {
            System.out.println("Error: Expected 1 argument but received " + args.length + ".");
            return 1;
        }

        try {
            LineCounter counter = new LineCounter();
            int count = counter.countVerticalLines(args[0]);
            System.out.println(count);
            return 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 2;
        }
    }
}
