package source;

import java.util.ArrayList;

public class Setup {

    private Map map;
    private String startingPoint;
    private ArrayList<String> chosenPlaces;
    private ArrayList<String> allPlaces;

    //GETTERS AND SETTERS

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
        if (allPlaces.contains(startingPoint)) {
            this.startingPoint = startingPoint;
        }
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


    //METHODS

    public boolean getReady() {
        this.map = map.deleteDiagonal();
        if ((this.map = map.fillGraph()) == null) {
            return false;
        }
        if (this.chosenPlaces != null) {
            if ((this.map = map.mapRestrict(chosenPlaces)) == null) {
                return false;
            }
        }
        return true;
    }


}
