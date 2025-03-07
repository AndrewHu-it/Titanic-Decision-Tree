//Samuel Jiang, Andrew Hurlbut, Danielle Gilbert
//AI
//Titanic
//March 4th, 2025

import java.io.IOException;

public class TitanicScoring {

    public static void print(Titanic.Passenger passenger, String message, boolean verbose) {
        String name = passenger.name();
        if (name.length() > 0) {
            System.out.println(name + ": " + message);
        } else {
            System.out.println(message);
        }

        if (verbose) {
            System.out.println("  ID       = " + passenger.id());
            System.out.println("  Sex      = " + passenger.sex());
            System.out.println("  Age      = " + passenger.age());
            System.out.println("  Port     = " + passenger.port());
            System.out.println("  Class    = " + passenger.pclass());
            System.out.println("  Parents  = " + passenger.parents());
            System.out.println("  Siblings = " + passenger.siblings());
            System.out.println("  Cabin    = " + passenger.cabin());
            System.out.println("  Ticket   = " + passenger.ticket());
            System.out.println("  Fare     = " + passenger.fare());
            System.out.println("  Survived = " + passenger.survived());
            System.out.println();
        }
    }


    public static void main(String[] args) {
        String filename = "test.tsv";
        boolean verbose = false;
        boolean silent = false;

        for (String arg : args) {
            switch(arg) {
                case "-silent":
                    silent = true;
                    continue;

                case "+silent":
                    silent = false;
                    continue;

                case "-verbose":
                    verbose = true;
                    continue;

                case "+verbose":
                    verbose = false;
                    continue;

                case "-":
                    filename = "";
                    continue;

                default:
                    if (arg.charAt(0) == '-') {
                        System.err.println("Unknown option: " + arg);
                        continue;
                    }
                    filename = arg;
            }
        }

        Titanic.Passenger[] passengers = null;
        try {
            passengers = Titanic.read(filename);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        int count = 0;
        int correct = 0;
        for (Titanic.Passenger passenger : passengers) {
            try {
                boolean survived = TitanicClassifier.survived(passenger);
                if (survived == passenger.survived()) {
                    correct++;
                } else if (!silent) {
                    print(passenger, "Incorrect classification", verbose);
                }
            } catch (Exception e) {
                print(passenger, "Unhandled exception (" + e + ")", verbose);
            }
            count++;
        }

        double accuracy = ((double) correct) / ((double) count);
        System.out.printf("Accuracy = %.2f\n", 100.0 * accuracy);
    }
}
