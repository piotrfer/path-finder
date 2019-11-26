package source;

import java.util.ArrayList;

public class Solver implements SolverInterface {

    public static Path solveMap(Setup setup) {

        int start = setup.getAllPlaces().indexOf(setup.getStartingPoint());
        int size = setup.getMap().getTimeMatrix().length;
        ArrayList<Solution> partSolution = new ArrayList<>();

        for (int i = 1; i <= size; i++) { //obróci się tyle razy ile miejsc ma odwiedzić
            ArrayList<int[]> combinations = generateCombinations(size, i);
            System.out.println("smt");
        }
        return null;
    }

    private static ArrayList<int[]> generateCombinations(int size, int k) {
        ArrayList<int[]> result = new ArrayList<>();
        for(int i = 0; i < size; i++){
            int[] digit = new int[1];
            digit[0] = i;
            result.add(digit);
        }

        for(int i = 2; i <= k; i++) {
            result = addDigit(result, k);
        }

        return result;
    }

    private static ArrayList<int[]> addDigit(ArrayList<int[]> given, int k){
        int size = given.size();
        for(int i = 0; i < size; i++){
            for(int j = 0; j < k; j++){
                int[] digit = new int[given.get(i).length + 1];
                digit[given.get(i).length] = k;
                given.add(digit);
            }
            given.remove(i);
        }
        return given;
    }

    private class Solution {
        private int finish;
        private ArrayList<Integer> by;
        private int time;
        private double price;
        private ArrayList<Integer> through;

        public Solution(int finish, ArrayList<Integer> by, int time, double price, ArrayList<Integer> through) {
            this.finish = finish;
            this.by = by;
            this.time = time;
            this.price = price;
            this.through = through;
        }

        public int getFinish() {
            return finish;
        }

        public ArrayList<Integer> getBy() {
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
