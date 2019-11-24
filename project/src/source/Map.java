package source;
import java.util.ArrayList;

public class Map {
    private int[][] timeMatrix;
    private ArrayList<String>[][] pointsThroughMatrix;
    private double[][] priceMatrix;
    private boolean isComplete = false;

    public Map(int[][] timeMatrix, double[][] priceMatrix) {
        this.timeMatrix = timeMatrix;
        this.priceMatrix = priceMatrix;
    }

    public boolean fillGraph(){
        return false;
    }

    public Map mapRestrict(ArrayList<String> chosenPoints){
        return null;
    }


    //tmp
    @Override
    public String toString(){
        StringBuilder b = new StringBuilder();
        b.append("TIME: \n");
        for(int i = 0; i < timeMatrix.length; i++){
            b.append("[ ");
            for(int j = 0; j < timeMatrix.length; j++){
                b.append(timeMatrix[i][j]).append(", ");
            }
            b.append(("] \n"));
        }
        b.append("\n PRICE: \n");
        for(int i = 0; i < priceMatrix.length; i++){
            b.append("[ ");
            for(int j = 0; j < priceMatrix.length; j++){
                b.append(priceMatrix[i][j]).append(", ");
            }
            b.append(("] \n"));
        }

        return b.toString();
    }

}
