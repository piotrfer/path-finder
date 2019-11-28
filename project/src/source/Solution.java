package source;

import java.util.ArrayList;
import java.util.Arrays;

public class Solution {

    private String finish;
    private String[] by;
    private int time;
    private double price;
    private ArrayList<String> through;

    public Solution(int time, double price, ArrayList<String> through) {
        this.time = time;
        this.price = price;
        this.through = through;
    }

    public Solution(String finish, String[] by) {
        this.finish = finish;
        this.by = by;
    }

    public String getFinish() {
        return finish;
    }

    public String[] getBy() {
        return by;
    }

    public int getTime() {
        return time;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<String> getThrough() {
        return through;
    }

    public void solve(Map map, int startingPointIndex, ArrayList<String> nameToIndex, ArrayList<Solution> partSolutions) {

        if (by.length == 0) {
            time = map.getTimeMatrix()[startingPointIndex][nameToIndex.indexOf(finish)];
            price = map.getPriceMatrix()[startingPointIndex][nameToIndex.indexOf(finish)];
            through = map.getPointsThroughMatrix()[startingPointIndex][nameToIndex.indexOf(finish)];
        } else {
            int minimumTime = Integer.MAX_VALUE;
            double minimumPrice = Double.MAX_VALUE;
            ArrayList<String> minimumThrough = new ArrayList<>();

            for (String k : by) {
                String[] tempBy = new String[by.length - 1];
                int n = 0;
                for (int j = 0; j < by.length; j++) {
                    if (by[j] != k) {
                        tempBy[n++] = by[j];
                    }
                }
                for (int i = 0; i < partSolutions.indexOf(this); i++) {
                    Solution currentSolution = partSolutions.get(i);
                    if (currentSolution.getBy().length == this.by.length - 1) {

                        if (currentSolution.getFinish() == k && Arrays.equals(currentSolution.getBy(), tempBy)) {
                            int midFinishIndex = nameToIndex.indexOf(currentSolution.getFinish());
                            int actualFinishIndex = nameToIndex.indexOf(finish);
                            int timeToCheck = currentSolution.getTime() + map.getTimeMatrix()[midFinishIndex][actualFinishIndex];
                            double priceToCheck = currentSolution.getPrice() + map.getPriceMatrix()[midFinishIndex][actualFinishIndex];

                            if (timeToCheck < minimumTime || (timeToCheck == minimumTime && priceToCheck < minimumPrice)) {
                                minimumTime = timeToCheck;
                                minimumPrice = priceToCheck;
                                minimumThrough = currentSolution.getThrough() != null ? (ArrayList<String>) currentSolution.getThrough().clone() : new ArrayList<>();
                                minimumThrough.add(currentSolution.getFinish());
                                if (map.getPointsThroughMatrix()[midFinishIndex][actualFinishIndex] != null) {
                                    minimumThrough.addAll(map.getPointsThroughMatrix()[midFinishIndex][actualFinishIndex]);
                                }
                            }
                        }
                    } else if (currentSolution.getBy().length < this.by.length - 1) {
                        partSolutions.remove(i);
                    }
                }
            }
            this.time = minimumTime;
            this.price = minimumPrice;
            this.through = minimumThrough;
        }
    }
}
