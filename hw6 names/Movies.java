// This program prompts for a phrase and it prints all movies from the
// imdb top-250 database that contain the phrase.

import java.io.*;
import java.util.*;

public class Movies {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("imdb.txt"));
        Scanner console = new Scanner(System.in);
        giveIntro();
        System.out.print("search phrase? ");
        String phrase = console.nextLine().toLowerCase();

        String line = find(input, phrase);
        int count = 0;
        if (line.length() > 0) {
            System.out.println("#\tRating\tVotes\tTitle");
            while (line.length() > 0) {
                print(line);
                line = find(input, phrase);
                count++;
            }
        }
        System.out.println(count + " matches");
    }

    // introduces the program to the user
    public static void giveIntro() {
        System.out.println("This program will allow you to search the");
        System.out.println("imdb top 250 movies for a particular phrase.");
        System.out.println();
    }

    // searches for and returns the next line of the given input that contains
    // the given phrase; returns an empty string if not found
    public static String find(Scanner input, String phrase) {
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.toLowerCase().contains(phrase)) {
                return line;
            }
        }
        return "";
    }

    // Prints the rank, rating, votes, and title separated by tabs for the
    // given line of the input file
    public static void print(String line) {
        Scanner data = new Scanner(line);
        int rank = data.nextInt();
        int votes = data.nextInt();
        double rating = data.nextDouble();
        System.out.print(rank + "\t" + rating + "\t" + votes + "\t");
        while (data.hasNext()) {
            System.out.print(data.next() + " ");
        }
        System.out.println();
    }
}