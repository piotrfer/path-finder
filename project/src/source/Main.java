package source;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program jest w trakcie produkcji");
        System.out.println(" ========================================== ");


        Setup mountainSetup;
        if((mountainSetup = Reader.setUpData(args)) == null){
            System.exit(1);
        }
        if(!mountainSetup.getReady()){
            System.exit(1);
        }

        System.out.println("AFTER FILLING");
        System.out.println(mountainSetup.getMap());
        System.out.println(mountainSetup.getAllPlaces());
        System.out.println(mountainSetup.getChosenPlaces());

        Path optimalPath;
        /*if( (optimalPath = Solver.solveMap(mountainSetup)) == null){
            System.exit(1);
        }
        if( !WriteOnScreen.showPath(optimalPath)){
            System.exit(1);
        } */


    }
}
