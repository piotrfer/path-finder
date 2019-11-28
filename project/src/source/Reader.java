package source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public abstract class Reader {

    public static Setup setUpData(String[] args) {
        String configPath = args.length < 1 ? null : args[0].strip();
        String startingPoint = args.length < 2 ? null : args[1].strip();
        String chosenPath = args.length < 3 ? null : args[2].strip();
        System.out.println("=============================");

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
        System.out.println("Argumenty wejściowe: wszystkie argumenty zostały wczytane.");

        Setup setup = readConfigFile(configPath);
        if (setup == null) {
            return null;
        }
        if (setup.getAllPlaces().contains(startingPoint)) {
            setup.setStartingPoint(startingPoint);
        } else {
            System.err.println("Argument wejściowy: punkt startowy " + startingPoint + " nie istnieje na mapie");
            return null;
        }

        if (chosenPath != null) {
            setup.setChosenPlaces(readChosenFile(chosenPath));
            if (setup.getChosenPlaces() == null) {
                System.err.println("Plik wybranych miejsc: krytyczny błąd odczytu pliku.");
                return null;
            }
            if (!setup.getChosenPlaces().contains(startingPoint) && setup.getAllPlaces().contains(startingPoint)) {
                ArrayList<String> chosenPlacesWithStart = setup.getChosenPlaces();
                chosenPlacesWithStart.add(startingPoint);
                setup.setChosenPlaces(chosenPlacesWithStart);
            }
        }
        return setup;
    }

    private static Setup readConfigFile(String path) {
        Setup readSetup = new Setup();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
            readSetup.setAllPlaces(readPlacesFromReader(reader));
            readSetup.setMap(readMapFromReader(reader, readSetup.getAllPlaces()));
            reader.close();
        } catch (IOException e) {
            System.err.println("Plik o podanej ścieżce: " + path + " nie istnieje.");
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
            if (currentLine.length() == 0 || currentLine.strip().charAt(0) == '#') {
                continue;
            }
            String[] words = currentLine.split("\\|");
            if (words[0].contains("#")) {
                continue;
            }
            if (words.length != 4) {
                return allPlaces;
            }
            if (!isNumber(words[0])) {
                continue;
            }

            reader.mark(0);
            String lp = words[0];
            String id = words[1].strip();
            if (allPlaces.contains(id)) {
                System.err.println("Plik konfiguracyjny: lp.: " + lp + ": Miejsce o id \"" + words[1] + "\" już zostało podane w pliku. Pomijam: \n" + currentLine + "\n");
            } else {
                allPlaces.add(id);
            }
        }
        return allPlaces;
    }

    private static boolean isNumber(String word) {
        return Pattern.matches("[^\\w]*\\d*[\\.]?[^\\w]", word);
    }

    private static Map readMapFromReader(BufferedReader reader, ArrayList<String> allPlaces) throws IOException {
        int[][] timeMatrix = new int[allPlaces.size()][allPlaces.size()];
        double[][] priceMatrix = new double[allPlaces.size()][allPlaces.size()];
        String currentLine;
        reader.reset();
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
            String lp = words[0].strip();
            String from = words[1].strip();
            String to = words[2].strip();
            String forward = words[3].strip();
            String backward = words[4].strip();
            String price = words[5].strip();

            if (!allPlaces.contains(from)) {
                System.err.println("Plik konfiguracyjny: lp.: " + lp + ": Miejsce " + from + " w siatce połączeń nie istnieje na mapie.");
                return null;
            }
            if (!allPlaces.contains(to)) {
                System.err.println("Plik konfiguracyjny: lp.: " + lp + ": Miejsce " + to + " w siatce połączeń nie istnieje na mapie.");
                return null;
            }

            int fromIndex = allPlaces.indexOf(from);
            int toIndex = allPlaces.indexOf(to);
            int forwardMinutes = timeConverter(forward);
            int backwardMinutes = timeConverter(backward);
            if (forwardMinutes <= 0 || backwardMinutes <= 0) {
                System.err.println("Plik konfiguracyjny: lp.: " + lp + "Czas podróży między punktami musi być większy od zera!");
                return null;
            }
            double priceInPln;
            try {
                priceInPln = getPrice(price);
            } catch (NumberFormatException e) {
                System.err.println("Plik konfiguracyjny: lp.: " + lp + "Podana kwota nie jest liczbą! Przyjmuję wstęp darmowy.");
                priceInPln = 0;
            }
            if (priceInPln < 0) {
                System.err.println("Plik konfiguracyjny: lp.: " + lp + ": Koszt trasy nie może być ujemny! Przyjmuję wstęp darmowy.");
                priceInPln = 0;
            }
            if (timeMatrix[fromIndex][toIndex] != 0) {
                System.err.println("Plik konfiguracyjny: lp.: " + lp + "Takie połączenie już istnieje! Pomijam linię.");
            } else {
                timeMatrix[fromIndex][toIndex] = forwardMinutes;
                timeMatrix[toIndex][fromIndex] = backwardMinutes;
                priceMatrix[fromIndex][toIndex] = priceInPln;
                priceMatrix[toIndex][fromIndex] = priceInPln;

            }
        }
        timeMatrix = fillBlanks(timeMatrix);
        return new Map(timeMatrix, priceMatrix);
    }

    private static int[][] fillBlanks(int[][] matrix) {
        int[][] newMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[i].length; j++) {
                newMatrix[i][j] = matrix[i][j] > 0 || i == j ? matrix[i][j] : Integer.MAX_VALUE;
            }
        }
        return newMatrix;
    }

    private static int timeConverter(String time) {
        int minutes = 0;
        time = time.strip();
        String[] parts = time.split(":");
        minutes += Integer.parseInt(parts[0]) * 60;
        minutes += Integer.parseInt(parts[1]);

        return minutes;
    }

    private static double getPrice(String price) throws NumberFormatException {
        price = price.strip();
        if (price.contains("--")) {
            return 0;
        } else {
            return Double.parseDouble(price);
        }
    }

    private static void showHelp() {
        System.out.println("Brak podanych argumentów wejściowych. Aby poprawnie uruchomić program podaj argumenty według następującego schematu:");
        System.out.println(".\\pathfinder data\\plik_konfiguracyjny nazwa_miejsca_startowego \\data\\wybrane_miejsca");
        System.out.println("Ostatni argument jest opcjonalny.");
        System.out.println("=============================");
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
                if (!isNumber(words[0]) || words.length < 2) {
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
