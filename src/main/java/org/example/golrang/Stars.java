package org.example.golrang;

import java.util.Scanner;

public class Stars {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        scanner.nextLine();
        String input = scanner.nextLine();

        String[] zero = {"***", "*.*", "***"};
        String[] one = {".*.", ".*.", ".*."};

        StringBuilder line1 = new StringBuilder();
        StringBuilder line2 = new StringBuilder();
        StringBuilder line3 = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (c == '0') {
                line1.append(zero[0]).append(".");
                line2.append(zero[1]).append(".");
                line3.append(zero[2]).append(".");
            } else if (c == '1') {
                line1.append(one[0]).append(".");
                line2.append(one[1]).append(".");
                line3.append(one[2]).append(".");
            }
        }

        line1.setLength(line1.length() - 1);
        line2.setLength(line2.length() - 1);
        line3.setLength(line3.length() - 1);

        System.out.println(line1);
        System.out.println(line2);
        System.out.println(line3);

    }
}
