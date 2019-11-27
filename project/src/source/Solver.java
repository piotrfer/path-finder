package source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Solver implements SolverInterface {

    public static Path solveMap(Setup setup) {

        int start = setup.getAllPlaces().indexOf(setup.getStartingPoint());
        int size = setup.getMap().getTimeMatrix().length;
        ArrayList<Solution> partSolution = new ArrayList<>();

        ArrayList<String> pointsToVisit = (ArrayList<String>) setup.getAllPlaces().clone();
        pointsToVisit.remove(start);

        ArrayList<String[]> powerSet = getPowerSet(pointsToVisit);

        for (int i = 0; i < powerSet.size(); i++) {
            for ( String k : pointsToVisit ){
                if( !hasIn(powerSet.get(i), k) ){
                    System.out.println(k + " | " + Arrays.toString(powerSet.get(i)));
                }
            }
        }


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
        result.sort(new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                return Integer.compare(o1.length, o2.length);
            }
        });

        return result;
    }

    private static boolean hasIn(String[] set, String key){
        for(int i = 0; i < set.length; i++){
            if( set[i] == key ){
                return true;
            }
        }
        return false;
    }

    private class Solution {
        private int finish;
        private String[] by;
        private int time;
        private double price;
        private ArrayList<Integer> through;

        public Solution(int finish, String[] by, int time, double price, ArrayList<Integer> through) {
            this.finish = finish;
            this.by = by;
            this.time = time;
            this.price = price;
            this.through = through;
        }

        public int getFinish() {
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

        public ArrayList<Integer> getThrough() {
            return through;
        }
    }
}
