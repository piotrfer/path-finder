package source;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program jest w trakcie testowania");
        System.out.println("===================================");


        Setup mountainSetup;
        if ((mountainSetup = Reader.setUpData(args)) == null) {
            System.exit(1);
        }
        if (!mountainSetup.getReady()) {
            System.exit(1);
        }

        Solution s;
        Solver heldKarp = new Solver();
        if ((s = heldKarp.solveMap(mountainSetup)) == null) {
            System.exit(1);
        }
        if( !WriteOnScreen.showPath(s, mountainSetup)){
            System.exit(1);
        }


    }
}
