package source;

public class PathFinder {
    public static void main(String[] args) {
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
        if (!WriteOnScreen.showPath(s, mountainSetup)) {
            System.exit(1);
        }
    }
}
