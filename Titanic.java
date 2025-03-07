//Samuel Jiang, Andrew Hurlbut, Danielle Gilbert
//AI
//Titanic
//March 4th, 2025

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;

public class Titanic {

    public static final int UNKNOWN = -1;

    public static enum Type {
        INTEGER,
        DOUBLE,
        STRING
    }

    public static enum Attribute {
        NAME     (Type.STRING),
        PORT     (Type.INTEGER),
        CLASS    (Type.INTEGER),
        SEX      (Type.INTEGER),
        AGE      (Type.DOUBLE),
        SIBLINGS (Type.INTEGER),
        PARENTS  (Type.INTEGER),
        FARE     (Type.DOUBLE),
        CABIN    (Type.STRING),
        TICKET   (Type.STRING);

        private Type type;

        private Attribute(Type type) {
            this.type = type;
        }

        public Type type() {
            return this.type;
        }

    }

    public static enum Sex   { MALE, FEMALE, UNKNOWN }
    public static enum Class { FIRST, SECOND, THIRD, UNKNOWN }
    public static enum Port  { CHERBOURG, QUEENSTOWN, SOUTHAMPTON, UNKNOWN }

    public static final int unknownAge       = -1;
    public static final int unknownSiblings  = -1;
    public static final int unknownParents   = -1;
    public static final double unknownFare   = -1.0;
    public static final String unknownName   = null;
    public static final String unknownCabin  = null;
    public static final String unknownTicket = null;


    public static class Passenger {

        private final int     id;
        private final String  name;
        private final Port    port;
        private final Class   pclass;
        private final Sex     sex;
        private final double  age;
        private final int     siblings;
        private final int     parents;
        private final double  fare;
        private final String  cabin;
        private final String  ticket;
        private final Boolean survived;

        public Passenger(
                int     id,
                String  name,
                Boolean survived,
                Port    port,
                Class   pclass,
                Sex     sex,
                double  age,
                int     siblings,
                int     parents,
                double  fare,
                String  cabin,
                String  ticket)
        {
            this.id       = id;
            this.name     = name;
            this.survived = survived;
            this.port     = port;
            this.pclass   = pclass;
            this.sex      = sex;
            this.age      = age;
            this.siblings = siblings;
            this.parents  = parents;
            this.fare     = fare;
            this.cabin    = cabin;
            this.ticket   = ticket;
        }

        public Passenger(
                int     id,
                String  name,
                Boolean survived,
                Port    port,
                Class   pclass,
                Sex     sex,
                double  age,
                int     siblings,
                int     parents,
                double  fare)
        {
            this.id       = id;
            this.name     = name;
            this.survived = survived;
            this.port     = port;
            this.pclass   = pclass;
            this.sex      = sex;
            this.age      = age;
            this.siblings = siblings;
            this.parents  = parents;
            this.fare     = fare;
            this.cabin    = null;
            this.ticket   = null;
        }

        int     id()       { return this.id; }
        String  name()     { return this.name; }
        Boolean survived() { return this.survived; }
        Port    port()     { return this.port; }
        Class   pclass()   { return this.pclass; }
        Sex     sex()      { return this.sex; }
        double  age()      { return this.age; }
        int     siblings() { return this.siblings; }
        int     parents()  { return this.parents; }
        double  fare()     { return this.fare; }
        String  cabin()    { return this.cabin; }
        String  ticket()   { return this.ticket; }

        private boolean missing(String s) {
            return s == null || s.length() == 0;
        }

        public boolean missing(Attribute attribute) {
            // Determines if the data for this attribute is missing for this passenger.
            switch(attribute) {
                case NAME:     return missing(this.name);
                case CLASS:    return this.pclass == Class.UNKNOWN;
                case PORT:     return this.port == Port.UNKNOWN;
                case SEX:      return this.sex == Sex.UNKNOWN;
                case AGE:      return this.age < 0.0;
                case SIBLINGS: return this.siblings < 0;
                case PARENTS:  return this.parents < 0;
                case FARE:     return this.fare < 0.0;
                case CABIN:    return missing(this.cabin);
                case TICKET:   return missing(this.ticket);
            }
            throw new IllegalArgumentException("Invalid attribute: " + attribute);
        }

        public String get(Attribute attribute) {
            // Returns the value of the specified attribute for this passenger as a string.
            if (missing(attribute)) return "UNKNOWN";
            switch (attribute) {
                case NAME:     return this.name;
                case CLASS:    return this.pclass.toString();
                case PORT:     return this.port.toString();
                case SEX:      return this.sex.toString();
                case AGE:      return Double.toString(this.age);
                case SIBLINGS: return Integer.toString(this.siblings);
                case PARENTS:  return Integer.toString(this.parents);
                case FARE:     return Double.toString(this.fare);
                case CABIN:    return this.cabin;
                case TICKET:   return this.ticket;
            }
            throw new IllegalArgumentException("Invalid attribute: " + attribute);
        }

        public int getInteger(Attribute attribute) {
            // Returns the value of the specified attribute for this passenger as an integer
            switch(attribute) {
                case CLASS:    return this.pclass.ordinal();
                case PORT:     return this.port.ordinal();
                case SEX:      return this.sex.ordinal();
                case AGE:      return (int) this.age;
                case SIBLINGS: return this.siblings;
                case PARENTS:  return this.parents;
                case FARE:     return (int) this.fare;
            }
            throw new IllegalArgumentException("Invalid attribute: " + attribute);
        }

        public double getDouble(Attribute attribute) {
            // Returns the value of the specified attribute for this passenger as an double
            switch(attribute) {
                case CLASS:    return this.pclass.ordinal();
                case PORT:     return this.port.ordinal();
                case SEX:      return this.sex.ordinal();
                case AGE:      return this.age;
                case SIBLINGS: return this.siblings;
                case PARENTS:  return this.parents;
                case FARE:     return this.fare;
            }
            throw new IllegalArgumentException("Invalid attribute: " + attribute);
        }

        public void print() {
            System.out.println(this.name);
            System.out.println("   ID       = " + this.id());
            System.out.println("   Sex      = " + this.sex());
            System.out.println("   Age      = " + this.age());
            System.out.println("   Port     = " + this.port());
            System.out.println("   Class    = " + this.pclass());
            System.out.println("   Parents  = " + this.parents());
            System.out.println("   Siblings = " + this.siblings());
            System.out.println("   Fare     = " + this.fare());
            System.out.println("   Cabin    = " + this.cabin());
            System.out.println("   Ticket   = " + this.ticket());
            System.out.println();
        }
    }

    public static Sex parseSex(String s) {
        switch (s.toUpperCase()) {
            case "MALE":   return Sex.MALE;
            case "FEMALE": return Sex.FEMALE;
            default:       return Sex.UNKNOWN;
        }
    }

    public static Class parseClass(String s) {
        try {
            return Class.values()[Integer.parseInt(s)-1];
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return Class.UNKNOWN;
        }
    }

    public static Port parsePort(String s) {
        switch (s.toUpperCase()) {
            case "C": case "CHERBOURG":   return Port.CHERBOURG;
            case "Q": case "QUEENSTOWN":  return Port.QUEENSTOWN;
            case "S": case "SOUTHAMPTON": return Port.SOUTHAMPTON;
            default:                      return Port.UNKNOWN;
        }
    }

    public static Boolean parseBoolean(String s) {
        try {
            switch (s.toUpperCase()) {
                case "FALSE": case "0": return false;
                case "TRUE":  case "1": return true;
                default:                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static int parseInteger(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return UNKNOWN;
        }
    }

    private static double parseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return UNKNOWN;
        }
    }

    public static Passenger[] read(Scanner scanner, boolean headers) {
        ArrayList<Passenger> list = new ArrayList<>();
        if (headers) scanner.nextLine();
        while (scanner.hasNextLine()) {
            String[] fields = scanner.nextLine().split("\t");

            // Tab separated fields:
            //     ID,Survived,Class,Name,Sex,Age,SibSp,ParCh,Ticket,Fare,Cabin,Port

            int     id       = 0;
            Boolean survived = false;
            Class   pclass   = Class.UNKNOWN;
            String  name     = unknownName;
            Sex     sex      = Sex.UNKNOWN;
            double  age      = unknownAge;
            int     siblings = unknownSiblings;
            int     parents  = unknownParents;
            String  ticket   = unknownTicket;
            double  fare     = unknownFare;
            String  cabin    = unknownCabin;
            Port    port     = Port.UNKNOWN;

            if (fields.length > 0) id = Integer.parseInt(fields[0]);
            if (fields.length > 1) survived = parseBoolean(fields[1]);
            if (fields.length > 2) pclass = parseClass(fields[2]);
            if (fields.length > 3) name = fields[3];
            if (fields.length > 4) sex = parseSex(fields[4]);
            if (fields.length > 5) age = parseDouble(fields[5]);
            if (fields.length > 6) siblings = parseInteger(fields[6]);
            if (fields.length > 7) parents = parseInteger(fields[7]);
            if (fields.length > 8) ticket = fields[8];
            if (fields.length > 9) fare = parseDouble(fields[9]);
            if (fields.length > 10) cabin = fields[10];
            if (fields.length > 11) port = parsePort(fields[11]);

            Passenger passenger =
                    new Passenger(id, name, survived, port, pclass, sex, age, siblings, parents, fare, cabin, ticket);
            list.add(passenger);
        }
        return list.toArray(new Passenger[]{});
    }

    public static Passenger[] read(Scanner scanner) {
        return read(scanner, true);
    }

    public static Passenger[] read(String filename, boolean headers) throws IOException {
        if (filename.length() > 0) {
            return read(new Scanner(new File(filename)), headers);
        } else {
            return read(new Scanner(System.in));
        }
    }

    public static Passenger[] read(String filename) throws IOException {
        return read(filename, true);
    }

    public static void write(Passenger[] passengers, boolean headers) {
        if (headers) {
            System.out.print("ID\t");
            System.out.print("Survived\t");
            System.out.print("Class\t");
            System.out.print("Name\t");
            System.out.print("Sex\t");
            System.out.print("Age\t");
            System.out.print("SibSp\t");
            System.out.print("ParCh\t");
            System.out.print("Ticket\t");
            System.out.print("Fare\t");
            System.out.print("Cabin\t");
            System.out.print("Port\t");
            System.out.println();
        }
        // Write out the passenger array as a tab separated value file
        for (Passenger passenger : passengers) {
            System.out.print(passenger.id());       System.out.print("\t");
            System.out.print(passenger.survived()); System.out.print("\t");
            System.out.print(passenger.pclass());   System.out.print("\t");
            System.out.print(passenger.name());     System.out.print("\t");
            System.out.print(passenger.sex());      System.out.print("\t");
            System.out.print(passenger.age());      System.out.print("\t");
            System.out.print(passenger.siblings()); System.out.print("\t");
            System.out.print(passenger.parents());  System.out.print("\t");
            System.out.print(passenger.ticket());   System.out.print("\t");
            System.out.print(passenger.fare());     System.out.print("\t");
            System.out.print(passenger.cabin());    System.out.print("\t");
            System.out.print(passenger.port());     System.out.println();
        }
    }

    public static void write(Passenger[] passengers) {
        write(passengers, true);
    }
}
