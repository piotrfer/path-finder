package source;

import java.util.ArrayList;
import java.util.Comparator;

public class Solver {

    public Solution solveMap(Setup setup) {
        ArrayList<Solution> partSolution = new ArrayList<>();

        ArrayList<String> pointsToVisit = (ArrayList<String>) (setup.getChosenPlaces() != null ? setup.getChosenPlaces().clone() : setup.getAllPlaces().clone());
        ArrayList<String> nameToIndexList = (ArrayList<String>) pointsToVisit.clone();
        int start = nameToIndexList.indexOf(setup.getStartingPoint());
        pointsToVisit.remove(setup.getStartingPoint());
        ArrayList<String[]> powerSet = getPowerSet(pointsToVisit);
        for (int i = 0; i < powerSet.size(); i++) {
            for (String k : pointsToVisit) {
                if (!hasIn(powerSet.get(i), k)) {
                    Solution currentSolution = new Solution(k, powerSet.get(i));
                    partSolution.add(currentSolution);
                    currentSolution.solve(setup.getMap(), start, nameToIndexList, partSolution);
                }

            }
        }

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

        return new Solution(minimumTime, minimumPrice, minimumThrough);
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
}
