package source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Reader {

    //PUBLIC

    public static Setup setUpData(String[] args) {

        String configPath = args.length < 1 ? null : args[0].strip();
        String startingPoint = args.length < 2 ? null : args[1].strip();
        String chosenPath = args.length < 3 ? null : args[2].strip();

        if (args.length == 0) {
            showHelp();
            return null;
        }

        if (configPath == null) {
            System.err.println("Argument wejściowy: brak podanego pliku konfiguracyjnego");
            return null;
        }
        if (startingPoint == null) {
            System.err.println("Argument wejściowy: brak podanego punktu startowego");
            return null;
        }

        Setup mountainSetup = readConfigFile(configPath);
        if (mountainSetup == null) {
            return null;
        }
        if (mountainSetup.getAllPlaces().contains(startingPoint)){
            mountainSetup.setStartingPoint(startingPoint);
        }
        else{
            System.err.println(mountainSetup.getAllPlaces());
            ArrayList<String> tmp = mountainSetup.getAllPlaces();
            System.err.println(tmp.contains(startingPoint));
            System.err.println("Argument wejściowy: punkt startowy " + startingPoint + " nie istnieje na mapie.");
            return null;
        }

        if (chosenPath != null) {
            mountainSetup.setChosenPlaces(readChosenFile(chosenPath)); //exception to handle - no such points
            if (mountainSetup == null) {
                System.err.println("Plik wybranych miejsc: krytyczny błąd odczytu pliku.");
                return null;
            }
        }

        return mountainSetup;
    }

    //PRIVATE


    private static Setup readConfigFile(String path) {
        Setup readSetup = new Setup();
        BufferedReader reader = null;


        try {
            reader = new BufferedReader(new FileReader(path));
            readSetup.setAllPlaces(readPlacesFromReader(reader));
            readSetup.setMap(readMapFromReader(reader, readSetup.getAllPlaces()));
            reader.close();
        } catch (
                IOException e) {
            System.err.println("Plik konfiguracyjny: krytyczny błąd odczytu pliku.");
        } finally {
            if (reader == null || readSetup.getMap() == null || readSetup.getAllPlaces() == null) {
                System.err.println("Plik konfiguracyjny: krytyczny błąd odczytu pliku.");
                return null;
            } else {
                System.out.println("Plik konfiguracyjny: odczyt zakończył się pomyślnie.");
                return readSetup;
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
            Place newPlace = new Place(words[1].strip(), words[2], words[3]);
            if (allPlaces.contains(newPlace.getId())) {
                System.err.println("Plik konfiguracyjny: Miejsce o id \"" + words[1] + "\" już zostało podane w pliku. Pomijam: \n" + currentLine + "\n" ); // if place already exist
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
            String from = words[1].strip();
            String to = words[2].strip();
            String forward = words[3].strip();
            String backward = words[4].strip();
            String price = words[5].strip();

            if (!allPlaces.contains(from)) {
                System.out.println("Plik konfiguracyjny: Miejsce " + from + " w siatce połączeń nie istnieje na mapie.");
                return null;
            }
            if (!allPlaces.contains(to)) {
                System.out.println("Plik konfiguracyjny: Miejsce " + to + " w siatce połączeń nie istnieje na mapie.");
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

        timeMatrix = fillBlanks(timeMatrix);

        return new Map(timeMatrix, priceMatrix);
    }

    private static int[][] fillBlanks(int[][] matrix){
        int[][] nmatrix = new int[matrix.length][matrix[0].length];
        for( int i = 0 ; i < nmatrix.length; i++){
            for( int j = 0; j < nmatrix[i].length; j++){
                nmatrix[i][j] = matrix[i][j] > 0 || i == j? matrix[i][j] : Integer.MAX_VALUE;
            }
        }
        return nmatrix;
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

    private static void showHelp() {
        System.out.println("Tu będzie wyświetlać się instrukcja obsługi programu.");
    }

    private static ArrayList<String> readChosenFile(String path) {
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
                String placeToAdd = words[1].strip();
                if (chosenPlaces.contains(placeToAdd)) {
                    System.err.println("Plik wybranych miejsc: Miejsce " + words[1] + " już zostało wybrane. Pomijam.");
                } else {
                    chosenPlaces.add(placeToAdd);
                }
            }
            if (chosenPlaces.size() != 0) {
                return chosenPlaces;
            } else {
                System.err.println("Plik wybranych miejsc: plik jest pusty.");
                return null;
            }
        } catch (IOException e) {
            System.err.println("Plik wybranych miejsc: krytyczny błąd odczytu pliku");
            return null;
        } finally {
            if (reader == null || chosenPlaces == null) {
                System.err.println("Plik wybranych miejsc: krytyczny błąd odczytu pliku");
                return null;
            } else {
                System.out.println("Plik wybranych miejsc: odczyt zakończył się pomyślnie.");
                return chosenPlaces;
            }

        }
    }
}
