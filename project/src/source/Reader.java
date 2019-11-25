package source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Reader {

    public static PathFinder setUpData(String[] args) {
        String configPath = args.length < 1 ? null : args[0];
        String startingPoint = args.length < 2 ? null : args[1];
        String chosenPath = args.length < 3 ? null : args[2];

        if (configPath == null) {
            System.err.println("You must provide config file path");
            return null;
        }
        if (startingPoint == null) {
            System.err.println("You must provide starting point id");
            return null;
        }

        PathFinder mountainPathFinder = readConfigFile(configPath);
        if (mountainPathFinder == null) {
            return null;
        }
        mountainPathFinder.setStartingPoint(startingPoint); //exception to handle - no such point


        if (chosenPath != null) {
            mountainPathFinder.setChosenPlaces(readChosenFile(chosenPath)); //exception to handle - no such points
            if( mountainPathFinder == null ){
                System.err.println("Cannot read chosen places file. Skiping");
            }
        }

        return mountainPathFinder;
    }


    public static PathFinder readConfigFile(String path) {
        PathFinder readPathFinder = new PathFinder();
        BufferedReader reader = null;


        try {
            reader = new BufferedReader(new FileReader(path));
            readPathFinder.setAllPlaces(readPlacesFromReader(reader));
            readPathFinder.setMap(readMapFromReader(reader, readPathFinder.getAllPlaces()));
            reader.close();
        } catch (
                IOException e) {
            System.err.println(e.getLocalizedMessage());
        } finally {
            if (reader == null || readPathFinder.getMap() == null || readPathFinder.getAllPlaces() == null) {
                System.err.println("Config data reading from file has failed");
                return null;
            } else {
                System.out.println("Config data has been extracted successfuly");
                return readPathFinder;
            }
        }
    }

    private static ArrayList<String> readPlacesFromReader(BufferedReader reader) throws IOException {
        String currentLine;
        ArrayList<String> allPlaces = new ArrayList<>();
        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.length() == 0 || currentLine.charAt(0) == '#') {
                continue;
            }
            String[] words = currentLine.split("\\|");
            if (words.length != 4) {
                return allPlaces;
            }
            if (!isNumber(words[0])) {
                continue;
            }
            reader.mark(0);
            Place newPlace = new Place(words[1], words[2], words[3]);
            if (allPlaces.contains(newPlace.getId())) {
                System.err.println("The place already exist"); // if place already exist
                return null;
            } else {
                allPlaces.add(newPlace.getId());
            }
        }
        return allPlaces;
    }

    private static boolean isNumber(String word) { //mało elegancka funkcja na razie
        word = word.strip();
        for (char c : word.toCharArray()) {
            if (!Character.isDigit(c) && c != '.') {
                return false;
            }
        }
        return true;
    }

    private static Map readMapFromReader(BufferedReader reader, ArrayList<String> allPlaces) throws IOException {
        int[][] timeMatrix = new int[allPlaces.size()][allPlaces.size()];
        double[][] priceMatrix = new double[allPlaces.size()][allPlaces.size()];
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.length() == 0 || currentLine.charAt(0) == '#') {
                continue;
            }
            String[] words = currentLine.split("\\|");
            if (words.length != 6) {
                return new Map(timeMatrix, priceMatrix);
            }
            if (!isNumber(words[0])) {
                continue;
            }
            String from = words[1];
            String to = words[2];
            String forward = words[3];
            String backward = words[4];
            String price = words[5];

            if (!allPlaces.contains(from)) {
                System.out.println("The place " + from + " doesn't exist");
                return null;
            }
            if (!allPlaces.contains(to)) {
                System.out.println("The place " + to + " doesn't exist");
                return null;
            }

            int fromIndex = allPlaces.indexOf(from);
            int toIndex = allPlaces.indexOf(to);
            int forwardMinutes = timeConverter(forward);
            int backwardMinutes = timeConverter(backward);
            double priceInPln = getPrice(price);

            timeMatrix[fromIndex][toIndex] = forwardMinutes;
            timeMatrix[toIndex][fromIndex] = backwardMinutes;
            priceMatrix[fromIndex][toIndex] = priceInPln;
            priceMatrix[toIndex][fromIndex] = priceInPln; //exception to handle = repeated route

        }
        return new Map(timeMatrix, priceMatrix);
    }

    private static int timeConverter(String time) { //exception to handle
        int minutes = 0;
        time = time.strip();
        String[] parts = time.split(":");
        minutes += Integer.parseInt(parts[0]) * 60;
        minutes += Integer.parseInt(parts[1]);

        return minutes;
    }

    private static double getPrice(String price) { //exception to handle
        price = price.strip();
        if (price == "--") {
            return 0;
        } else {
            try {
                return Double.parseDouble(price);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

    }

    public static ArrayList<String> readChosenFile(String path) {
        ArrayList<String> chosenPlaces = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
            String currentLine;
            chosenPlaces = new ArrayList<>();
            while ((currentLine = reader.readLine()) != null) {

                if (currentLine.length() == 0 || currentLine.charAt(0) == '#') {
                    continue;
                }
                String[] words = currentLine.split("\\|");
                if (!isNumber(words[0])) {
                    continue;
                }
                if (chosenPlaces.contains(words[1])) {
                    System.err.println("Place " + words[1] + " already exist. Skipping.");
                } else {
                    chosenPlaces.add(words[1]);
                }
            }
            if (chosenPlaces.size() != 0) {
                return chosenPlaces;
            } else {
                System.err.println("The chosen file is empty");
                return null;
            }
        } catch (IOException e) {
            System.err.println("Invalid chosen places file.");
            System.err.println("Data extraction from chosen places file has failed.");
            return null;
        } finally {
            if (reader == null || chosenPlaces == null) {
                System.err.println("Data extraction from chosen places file has failed.");
                return null;
            }
            else{
                return chosenPlaces;
            }

        }
    }
}
