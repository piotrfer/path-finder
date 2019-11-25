package source;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program is in the early stage of development");

        PathFinder mountainPathFinder = Reader.setUpData(args);

        System.out.println(mountainPathFinder.getMap());
        System.out.println(mountainPathFinder.getAllPlaces());
        System.out.println(mountainPathFinder.getChosenPlaces());

        if( mountainPathFinder == null ){
            System.exit(1);
        }


    }
}
