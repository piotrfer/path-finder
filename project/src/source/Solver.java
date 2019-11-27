package source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Solver implements SolverInterface {

    public Path solveMap(Setup setup) {

        int start = setup.getAllPlaces().indexOf(setup.getStartingPoint());
        ArrayList<Solution> partSolution = new ArrayList<>();

        ArrayList<String> pointsToVisit = (ArrayList<String>) (setup.getChosenPlaces() != null ? setup.getChosenPlaces().clone() : setup.getAllPlaces().clone());
        ArrayList<String> nameToIndexList = (ArrayList<String>) pointsToVisit.clone();
        pointsToVisit.remove(start);


        ArrayList<String[]> powerSet = getPowerSet(pointsToVisit);

        for (int i = 0; i < powerSet.size(); i++) {
            for (String k : pointsToVisit) {
                if (!hasIn(powerSet.get(i), k)) {
                    Solution currentSolution = new Solution(k, powerSet.get(i));
                    partSolution.add(currentSolution);
                    //System.out.println(currentSolution);
                    currentSolution.solve(setup.getMap(), setup.getAllPlaces().get(start), start, nameToIndexList, partSolution);
                    //System.out.println("-> " + currentSolution);
                    /*for (int j = 0; j < partSolution.size(); j++) {
                        System.out.println("##### " + partSolution.get(j));
                    }*/
                }
            }
        }

        //GET THE MINIMUM OUT OF
        int minimumTime = Integer.MAX_VALUE;
        double minimumPrice = Double.MAX_VALUE;
        ArrayList<String> minimumThrough = new ArrayList<>();

        for (Solution s : partSolution) {
            if (s.getBy().length == pointsToVisit.size() - 1) {
                int finish = nameToIndexList.indexOf(s.getFinish());
                int currentTime = s.getTime() + setup.getMap().getTimeMatrix()[finish][start];
                double currentPrice = s.getPrice() + setup.getMap().getPriceMatrix()[finish][start];
                ArrayList<String> through = s.getThrough() != null ? (ArrayList<String>) s.getThrough().clone() : new ArrayList<>();
                through.add(s.getFinish());
                if (setup.getMap().getPointsThroughMatrix()[finish][start] != null) {
                    through.addAll(setup.getMap().getPointsThroughMatrix()[finish][start]);
                }
                if (currentTime < minimumTime || (currentTime == minimumTime && currentPrice < minimumPrice)) {
                    minimumTime = currentTime;
                    minimumPrice = currentPrice;
                    minimumThrough = through;
                }


            }

        }

        System.out.println("RESULTS " + minimumTime + " " + minimumPrice + " " + minimumThrough);


        return null;
    }

    private static ArrayList<String[]> getPowerSet(ArrayList<String> pointsToVisit) {
        //funkcja na podstawie kodu zamieszczonego na stronie https://www.geeksforgeeks.org/power-set/
        int size = (int) Math.pow(2, pointsToVisit.size());
        ArrayList<String[]> result = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            String[] digits = new String[Integer.bitCount(i)];
            int n = 0;
            for (int j = 0; j < size; j++) {
                if ((i & (1 << j)) > 0) {
                    digits[n++] = pointsToVisit.get(j);
                }
            }
            result.add(digits);
        }
        result.sort(Comparator.comparingInt(o -> o.length));

        return result;
    }

    private static boolean hasIn(String[] set, String key) {
        for (int i = 0; i < set.length; i++) {
            if (set[i] == key) {
                return true;
            }
        }
        return false;
    }

    private class Solution {
        private String finish;
        private String[] by;
        private int time;
        private double price;
        private ArrayList<String> through;// = new ArrayList<>();

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

        public void solve(Map map, String startingPoint, int startingPointIndex, ArrayList<String> nameToIndex, ArrayList<Solution> partSolutions) {

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

                                //System.out.println("----" + currentSolution);

                                if (timeToCheck < minimumTime || (timeToCheck == minimumTime && priceToCheck < minimumPrice)) {
                                    minimumTime = timeToCheck;
                                    minimumPrice = priceToCheck;
                                    minimumThrough = currentSolution.getThrough() != null ? (ArrayList<String>) currentSolution.getThrough().clone() : new ArrayList<>();
                                    minimumThrough.add(currentSolution.getFinish());
                                    if (map.getPointsThroughMatrix()[midFinishIndex][actualFinishIndex] != null) {
                                        minimumThrough.addAll(map.getPointsThroughMatrix()[midFinishIndex][actualFinishIndex]);
                                    }
                                    //System.out.println("----" + currentSolution);
                                    //System.out.println("----" + currentSolution.getThrough() + " + " + currentSolution.getFinish());
                                    //System.out.println("----" + startingPointIndex + " -> " + midFinishIndex + " -> " + actualFinishIndex);


                                }
                            }
                        }
                    }
                }
                this.time = minimumTime;
                this.price = minimumPrice;
                this.through = minimumThrough;
            }
        }


        @Override
        public String toString() {
            StringBuilder b = new StringBuilder();
            b.append("A({ ");
            for (int i = 0; i < by.length; i++) {
                b.append(by[i]).append(", ");
            }
            b.append("}, ").append(finish).append(") = time: ").append(time).append(" price: ").append(price).append(" through: [ ");
            if (through != null) {
                for (int i = 0; i < through.size(); i++) {
                    b.append(through.get(i)).append(", ");
                }
            }
            b.append("]");

            return b.toString();
        }

    }
}
