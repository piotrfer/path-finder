package source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Reader {
    public static boolean readConfigFile(String path, Map map, ArrayList<String> allPlaces) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
            while (!reader.ready()) ;
            allPlaces = readPlacesFromReader(reader);
            map = readMapFromReader(reader, allPlaces);
            System.out.println(allPlaces);
            System.out.println(map);
        } catch (
                IOException e) {
            System.err.println(e.getLocalizedMessage());
        } finally {
            if (reader == null || map == null || allPlaces == null) {
                System.out.println(map);
                System.err.println("Data reading from file has failed");
                return false;
            } else {
                System.out.println("Data has been extracted successfuly");
                return true;
            }
        }
    }

    private static ArrayList<String> readPlacesFromReader(BufferedReader reader) throws IOException {
        String currentLine = "";
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

            if (!allPlaces.contains(from) || !allPlaces.contains(to)) {
                System.out.println("The place doesn't exist");
                return null;
            }

            int fromIndex = allPlaces.indexOf(from);
            int toIndex = allPlaces.indexOf(to);
            int forwardMinutes = timeConverter(forward);
            int backwardMinutes = timeConverter(backward);
            double priceInPln = getPrice( price );

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

    private static double getPrice( String price){ //exception to handle
        price = price.strip();
        if( price == "--" ){
            return 0;
        }
        else{
            try{
                return Double.parseDouble(price);
            } catch( NumberFormatException e){
                return 0;
            }
        }

    }

    public static boolean readChosenFile(String path, ArrayList<String> chosenPlaces) {
        return false;
    }
}
