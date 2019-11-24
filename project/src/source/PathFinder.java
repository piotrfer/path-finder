package source;

import java.util.ArrayList;
import java.util.HashMap;

public class PathFinder {

    public static void main(String args[]) {
        Map map = null;
        String startingPoint;
        ArrayList<String> chosenPlaces = new ArrayList<>();
        ArrayList<String> allPlaces = new ArrayList<>();

        System.out.println("Program is in the early stage of development");

        Reader.readConfigFile("C:\\Users\\piotr\\OneDrive\\Pulpit\\data\\config.txt", map, allPlaces);
        System.out.println(map);
        System.out.println(allPlaces);
    }

}
