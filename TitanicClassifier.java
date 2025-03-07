//Samuel Jiang, Andrew Hurlbut, Danielle Gilbert
//AI
//Titanic
//March 4th, 2025

public class TitanicClassifier {

    private static String getSection(Titanic.Passenger passenger) {
        String cabin = passenger.cabin();
        if (cabin == null || cabin.trim().isEmpty()) {
            return "N/A";
        } else {
            return cabin.trim().substring(0, 1);
        }
    }

    public static boolean survived(Titanic.Passenger passenger) {
        double chance = 0.385;
        if (passenger.sex() == Titanic.Sex.MALE) {
            if (passenger.pclass() == Titanic.Class.FIRST) {
                String section = getSection(passenger);
                System.out.println("Cabin Section: " + section);
                if ("N/A".equals(section)) {
                    chance = (passenger.age() <= 28) ? 0.25 : 0.22;
                } else if ("A".equals(section)) {
                    chance = (passenger.age() <= 28) ? 0.67 : 0.44;
                } else if ("B".equals(section)) {
                    chance = (passenger.age() <= 28) ? 0.3333 : 0.428;
                } else if ("C".equals(section)) {
                    chance = (passenger.age() <= 28) ? 0.44 : 0.238;
                } else if ("D".equals(section)) {
                    chance = (passenger.age() <= 28) ? 0.6 : 0.375;
                } else if ("E".equals(section)) {
                    chance = (passenger.age() <= 28) ? 1.0 : 0.4167;
                } else {
                    chance = 0.3661;
                }
            } else if (passenger.pclass() == Titanic.Class.SECOND) {
                int parents = passenger.parents();
                if (parents == 0) {
                    chance = (passenger.age() <= 28) ? 0.0344 : 0.1346;
                } else {
                    chance = 0.25;
                }
            } else if (passenger.pclass() == Titanic.Class.THIRD) {
                String section = getSection(passenger);
                if ("N/A".equals(section)) {
                    chance = 0.129;
                } else if ("E".equals(section)) {
                    chance = 1.0;
                } else if ("F".equals(section)) {
                    chance = 0.0;
                } else {
                    chance = 0.12913;
                }
            }else{
                System.out.println("No class found");
            }
        } else if (passenger.sex() == Titanic.Sex.FEMALE) {
            if (passenger.pclass() == Titanic.Class.FIRST) {
                int parents = passenger.parents();
                if (parents == 0) {
                    chance = 0.98275;
                } else if (parents == 1) {
                    chance = 1.0;
                } else if (parents == 2) {
                    chance = 0.84615;
                } else {
                    chance = 0.963414;
                }
            } else if (passenger.pclass() == Titanic.Class.SECOND) {
                int parents = passenger.parents();
                if (parents == 0) {
                    chance = 0.9024;
                } else if (parents == 1) {
                    chance = 0.9375;
                } else if (parents == 2) {
                    chance = 1.0;
                } else if (parents == 3) {
                    chance = 1.0;
                } else {
                    chance = 0.92857;
                }
            } else if (passenger.pclass() == Titanic.Class.THIRD) {
                int siblings = passenger.siblings();
                if (siblings == 0) {
                    int parents = passenger.parents();
                    if (parents == 0) {
                        chance = 0.6428;
                    } else if (parents == 1) {
                        chance = 0.4;
                    } else if (parents == 2) {
                        chance = 0.6;
                    } else {
                        chance = 0.60273;
                    }
                } else if (siblings == 1) {
                    int parents = passenger.parents();
                    if (parents == 0) {
                        chance = 0.476;
                    } else if (parents == 1) {
                        chance = 0.555;
                    } else {
                        chance = 0.45714;
                    }
                } else if (siblings == 2) {
                    chance = 0.5714;
                } else if (siblings == 3) {
                    chance = 0.125;
                } else if (siblings == 4) {
                    chance = 0.4;
                } else {
                    chance = 0.51145;
                }
            }
        }

        double randomValue = Math.random();
//        System.out.println("Chance: " + chance + ", Random Value: " + randomValue);
        return (randomValue <= chance);
    }
}
