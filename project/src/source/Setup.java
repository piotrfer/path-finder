package source;

import java.util.ArrayList;

public class Setup {

    private Map map;
    private String startingPoint;
    private ArrayList<String> chosenPlaces;
    private ArrayList<String> allPlaces;

    public Setup() {
    }

    public Setup(Map map, String startingPoint, ArrayList<String> chosenPlaces, ArrayList<String> allPlaces) {
        this.map = map;
        this.startingPoint = startingPoint;
        this.chosenPlaces = chosenPlaces;
        this.allPlaces = allPlaces;
    }

    public boolean getReady() {
        if (!map.fillGraph(allPlaces)) {
            return false;
        }
        if (this.chosenPlaces != null) {
            if (!map.mapRestrict(chosenPlaces, allPlaces)) {
                return false;
            }
        }
        System.out.println("Przygotowanie danych przebiegło pomyślnie.");
        return true;
    }

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

    @Override
    public boolean equals(Object o) {
        if( o == null){
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Setup no = (Setup) o;
        if (this.map.equals(no.map) && this.startingPoint.equals(no.startingPoint) && this.allPlaces.equals(no.allPlaces)) {
            if( (this.chosenPlaces == null && no.chosenPlaces == null) || this.chosenPlaces.equals(no.chosenPlaces)) {
                return true;
            }
            else{
                System.err.println("chosen");
                return false;
            }
        } else {
            System.err.println(this.map.equals(no.map));
            System.err.println(this.startingPoint.equals(no.startingPoint));
            System.err.println(this.allPlaces.equals(no.allPlaces));
            return false;
        }
    }
}
