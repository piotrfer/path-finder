package source;

import java.util.ArrayList;
import java.util.Arrays;

public class Map {

    private int[][] timeMatrix;
    private ArrayList<String>[][] pointsThroughMatrix;
    private double[][] priceMatrix;

    public boolean fillGraph(ArrayList<String> allPlaces) {
        int[][] timeMatrix = this.timeMatrix;
        double[][] priceMatrix = this.priceMatrix;
        ArrayList<String>[][] pointsThrough = new ArrayList[timeMatrix.length][timeMatrix.length];

        for (int start = 0; start < timeMatrix.length; start++) {
            ArrayList<String> queue = (ArrayList<String>) allPlaces.clone();
            ArrayList<String> seen = new ArrayList<>();

            int[] byTimeArray = timeMatrix[start];
            while (queue.size() != 0) {
                int by = getMinimumIndex(byTimeArray, queue, allPlaces);
                queue.remove(allPlaces.get(by));
                seen.add(allPlaces.get(by));

                byTimeArray = timeMatrix[by];
                for (int finish = 0; finish < byTimeArray.length; finish++) {
                    if (byTimeArray[finish] == Integer.MAX_VALUE || timeMatrix[start][by] == Integer.MAX_VALUE) {
                        continue;
                    }
                    int timeBy = timeMatrix[start][by] + byTimeArray[finish];
                    double priceBy = priceMatrix[start][by] + priceMatrix[by][finish];
                    if (timeBy < timeMatrix[start][finish] || (timeBy == timeMatrix[start][finish] && priceBy < priceMatrix[start][finish])) {

                        timeMatrix[start][finish] = timeBy;
                        pointsThrough[start][finish] = new ArrayList<>();

                        if (pointsThrough[start][by] != null) {
                            pointsThrough[start][finish].addAll(pointsThrough[start][by]);
                        }

                        pointsThrough[start][finish].add(allPlaces.get(by));

                        if (pointsThrough[by][finish] != null) {
                            pointsThrough[start][finish].addAll(pointsThrough[by][finish]);
                        }

                        priceMatrix[start][finish] = priceBy;
                    }
                }
            }
        }
        this.pointsThroughMatrix = pointsThrough;

        if (!isGraphConsistent(timeMatrix, allPlaces)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isGraphConsistent(int[][] timeMatrix, ArrayList<String> allPlaces) {
        for (int i = 0; i < timeMatrix.length - 1; i++) {
            for (int j = 0; j < timeMatrix[i].length; j++) {
                if (timeMatrix[i][j] == Integer.MAX_VALUE) {
                    int detached;
                    if (timeMatrix[i + 1][j] == Integer.MAX_VALUE) {
                        detached = j;
                    } else {
                        detached = i;
                    }
                    System.err.println("Graf jest niespójny. Wierzchołek " + allPlaces.get(detached) + " nie jest połączony z innymi.");
                    return false;
                }
            }
        }
        return true;
    }

    private int getMinimumIndex(int[] array, ArrayList<String> queue, ArrayList<String> allPoints) {
        if (array == null || array.length == 0) {
            return -1;
        } else {
            int minValue = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int i = 0; i < array.length; i++) {
                if (!queue.contains(allPoints.get(i))) {
                    continue;
                }
                if (minValue > array[i]) {
                    minValue = array[i];
                    minIndex = i;
                }
            }
            if (minIndex != -1) {
                return minIndex;
            } else if (!queue.isEmpty()) {
                return allPoints.indexOf(queue.get(0));
            } else {
                return -1;
            }
        }
    }

    public boolean mapRestrict(ArrayList<String> chosenPoints, ArrayList<String> allPlaces) {
        int size = chosenPoints.size();
        int[][] ntimeMatrix = new int[size][size];
        double[][] npriceMatrix = new double[size][size];
        ArrayList<String>[][] npointsThrough = new ArrayList[size][size];
        ArrayList<Integer> common = new ArrayList<>();

        for (int i = 0; i < allPlaces.size(); i++) {
            if (chosenPoints.contains(allPlaces.get(i))) {
                common.add(i);
            }
        }
        int n = 0;
        int m = 0;
        for (int i : common) {
            for (int j : common) {
                ntimeMatrix[n][m] = this.timeMatrix[i][j];
                npriceMatrix[n][m] = this.priceMatrix[i][j];
                npointsThrough[n][m] = this.pointsThroughMatrix[i][j];
                m++;
            }
            n++;
            m = 0;
        }
        this.timeMatrix = ntimeMatrix;
        this.pointsThroughMatrix = npointsThrough;
        this.priceMatrix = npriceMatrix;
        return true;
    }

    public int[][] getTimeMatrix() {
        return timeMatrix;
    }

    public ArrayList<String>[][] getPointsThroughMatrix() {
        return pointsThroughMatrix;
    }

    public double[][] getPriceMatrix() {
        return priceMatrix;
    }

    public Map(int[][] timeMatrix, double[][] priceMatrix) {
        this.timeMatrix = timeMatrix;
        this.priceMatrix = priceMatrix;
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Map no = (Map) o;
        if (Arrays.equals(this.pointsThroughMatrix, no.pointsThroughMatrix)) {
            for (int i = 0; i < this.priceMatrix.length; i++) {
                if (!Arrays.equals(this.timeMatrix[i], no.timeMatrix[i]) || !Arrays.equals(this.priceMatrix[i], no.priceMatrix[i])) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
