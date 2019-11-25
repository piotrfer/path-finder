package source;

import java.util.ArrayList;

public class PathFinder {

    private Map map;
    private String startingPoint;
    private ArrayList<String> chosenPlaces;
    private ArrayList<String> allPlaces;

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public ArrayList<String> getChosenPlaces() {
        return chosenPlaces;
    }

    public void setChosenPlaces(ArrayList<String> chosenPlaces) {
        this.chosenPlaces = chosenPlaces;
    }

    public ArrayList<String> getAllPlaces() {
        return allPlaces;
    }

    public void setAllPlaces(ArrayList<String> allPlaces) {
        this.allPlaces = allPlaces;
    }


}
