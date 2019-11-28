package source;

public class PathFinder {

    public static void main(String[] args) {
        Setup setup;
        if ((setup = Reader.setUpData(args)) == null) {
            System.exit(1);
        }
        if (!setup.getReady()) {
            System.exit(1);
        }
        Solution s;
        Solver heldKarp = new Solver();
        if ((s = heldKarp.solveMap(setup)) == null) {
            System.exit(1);
        }
        if (!WriteOnScreen.showPath(s, setup)) {
            System.exit(1);
        }
    }
}
