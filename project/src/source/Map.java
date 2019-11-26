package source;

import java.util.ArrayList;

public class Map {
    private int[][] timeMatrix;
    private ArrayList<String>[][] pointsThroughMatrix;
    private double[][] priceMatrix;

    public Map(int[][] timeMatrix, double[][] priceMatrix) {
        this.timeMatrix = timeMatrix;
        this.priceMatrix = priceMatrix;
    }

    public boolean fillGraph(ArrayList<String> allPlaces) {
        int[][] timeMatrix = this.timeMatrix;
        double[][] priceMatrix = this.priceMatrix;
        ArrayList<String>[][] pointsThrough = new ArrayList[timeMatrix.length][timeMatrix.length];

        for (int i = 0; i < timeMatrix.length; i++) {
            ArrayList<String> queue = (ArrayList<String>) allPlaces.clone();
            ArrayList<String> seen = new ArrayList<>();

            int[] currentPointArray = timeMatrix[i];
            while (queue.size() != 0) {
                int minimumIndex = getMinimumIndex(currentPointArray, seen, allPlaces);
                int minimumValue = currentPointArray[minimumIndex];
                queue.remove(allPlaces.get(minimumIndex));
                seen.add(allPlaces.get(minimumIndex));

                currentPointArray = timeMatrix[minimumIndex];
                for (int j = 0; j < currentPointArray.length; j++) {
                    if (currentPointArray[j] == Integer.MAX_VALUE) {
                        continue;
                    }
                    if (minimumValue + timeMatrix[i][minimumIndex] + currentPointArray[j] < timeMatrix[i][j]) {//jeżeli droga przez dany wierzchołek jest mniejsza
                        timeMatrix[i][j] = minimumValue + currentPointArray[j];
                        pointsThrough[i][j] = new ArrayList<>();
                        if( pointsThrough[i][minimumIndex] != null){
                            pointsThrough[i][j].addAll(pointsThrough[i][minimumIndex]);
                        }
                        pointsThrough[i][j].add(allPlaces.get(minimumIndex));
                    }
                }
            }
        }
        this.pointsThroughMatrix = pointsThrough;
        return true;
    }

    private int getMinimumIndex(int[] array, ArrayList<String> seen, ArrayList<String> allPoints) {
        if (array == null || array.length == 0) {
            return -1;
        } else {
            int minValue = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int i = 0; i < array.length; i++) {
                if( seen.contains(allPoints.get(i)))
                    continue;
                if (minValue > array[i]) {
                    minValue = array[i];
                    minIndex = i;
                }
            }
            return minIndex != - 1? minIndex: !seen.isEmpty()? allPoints.indexOf(seen.get(0)): -1;
        }
    }

    public Map mapRestrict(ArrayList<String> chosenPoints) {
        return null;
    }

    public Map deleteDiagonal() {
        int[][] timeMatrix = this.timeMatrix;
        double[][] priceMatrix = this.priceMatrix;
        for (int i = 0; i < timeMatrix.length; i++) {
            timeMatrix[i][i] = -1;
            priceMatrix[i][i] = -1;
        }
        return new Map(timeMatrix, priceMatrix);
    }


    //tmp
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("TIME: \n");
        for (int i = 0; i < timeMatrix.length; i++) {
            b.append("[ ");
            for (int j = 0; j < timeMatrix.length; j++) {
                b.append(timeMatrix[i][j]).append(", ");
            }
            b.append(("] \n"));
        }
        b.append("\n PRICE: \n");
        for (int i = 0; i < priceMatrix.length; i++) {
            b.append("[ ");
            for (int j = 0; j < priceMatrix.length; j++) {
                b.append(priceMatrix[i][j]).append(", ");
            }
            b.append(("] \n"));
        }

        return b.toString();
    }


    /*
    @Override
    public Map clone() {
        int[][] timeMatrix;
        ArrayList<String>[][] pointsThroughMatrix;
        double[][] priceMatrix;
        boolean isComplete = false;
    } */

}
