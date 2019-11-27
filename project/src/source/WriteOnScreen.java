package source;

public class WriteOnScreen implements ShowPathInterface {

    public static boolean showPath(Solution sol, Setup setup) {
        String start = setup.getStartingPoint();
        if( sol.getThrough() == null){
            System.err.println("Scieżka jest pusta.");
            return false;
        }
        System.out.println("Optymalna ścieżka:");
        System.out.print(start + " -> ");
        for (int i = 0; i < sol.getThrough().size(); i++) {
            System.out.print("-> " + sol.getThrough().get(i));
        }
        System.out.print("-> " + start + "\n");
        System.out.println("Czas: " + getHoursAndMinutes(sol.getTime()));
        System.out.println("Koszt: " + sol.getPrice());
        return true;
    }

    private static String getHoursAndMinutes(int time) {
        StringBuilder b = new StringBuilder();
        b.append((int)Math.floor(time / 60)).append(" godz. ").append(time % 60).append(" min.");
        return b.toString();
    }
}
