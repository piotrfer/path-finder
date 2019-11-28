package source.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import source.Map;
import source.Reader;
import source.Setup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ReaderTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void shouldCorrectlyReadSmallFile() {
        File file1 = new File("src/source/test/data/reader_1.txt");

        Setup result = Reader.setUpData(new String[]{file1.getAbsolutePath(), "A"});

        Map expectedMap = new Map(new int[][]{
                new int[]{0, 120, 240, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                new int[]{180, 0, 90, 60, 60, Integer.MAX_VALUE},
                new int[]{270, 90, 0, 180, Integer.MAX_VALUE, Integer.MAX_VALUE},
                new int[]{Integer.MAX_VALUE, 90, 120, 0, Integer.MAX_VALUE, 240},
                new int[]{Integer.MAX_VALUE, 90, Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 30},
                new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 180, 30, 0}},
                new double[][]{
                        new double[]{0, 0, 5, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0},
                        new double[]{5, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0}
                }
        );
        ArrayList<String> expectedAllPlaces = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F"));
        Setup expected = new Setup(expectedMap, "A", null, expectedAllPlaces);

        if (expected.equals(result)) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test
    public void shouldCorrectlyReadBigFile() {
        File file1 = new File("src/source/test/data/reader_2.txt");

        Setup result = Reader.setUpData(new String[]{file1.getAbsolutePath(), "F"});

        Map expectedMap = new Map(new int[][]{
                new int[]{0, 60, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 60},
                new int[]{60, 0, 60, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                new int[]{Integer.MAX_VALUE, 60, 0, 60, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, 60, 0, 60, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 60, 0, 60, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 60, 0, 60, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 60, 0, 60, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 60, 0, 60, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 60, 0, 60, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 60, 0, 60, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 60, 0, 60, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 60, 0, 60, Integer.MAX_VALUE, Integer.MAX_VALUE},
                new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 60, 0, 60, Integer.MAX_VALUE},
                new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 60, 0, 60},
                new int[]{60, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 60, 0},},
                new double[][]{
                        new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 5.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        new double[]{0, 5.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},}
        );
        ArrayList<String> expectedAllPlaces = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"));
        Setup expected = new Setup(expectedMap, "F", null, expectedAllPlaces);

        if (expected.equals(result)) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test
    public void shouldCorrectlyReadChosenPointFile() {
        File file1 = new File("src/source/test/data/reader_1.txt");
        File file2 = new File("src/source/test/data/reader_3.txt");

        Setup result = Reader.setUpData(new String[]{file1.getAbsolutePath(), "A", file2.getAbsolutePath()});

        Map expectedMap = new Map(new int[][]{
                new int[]{0, 120, 240, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                new int[]{180, 0, 90, 60, 60, Integer.MAX_VALUE},
                new int[]{270, 90, 0, 180, Integer.MAX_VALUE, Integer.MAX_VALUE},
                new int[]{Integer.MAX_VALUE, 90, 120, 0, Integer.MAX_VALUE, 240},
                new int[]{Integer.MAX_VALUE, 90, Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 30},
                new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 180, 30, 0}},
                new double[][]{
                        new double[]{0, 0, 5, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0},
                        new double[]{5, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0},
                        new double[]{0, 0, 0, 0, 0, 0}
                }
        );
        ArrayList<String> expectedAllPlaces = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F"));
        ArrayList<String> expectedChosenPlaces = new ArrayList<>(Arrays.asList("A", "B", "E"));
        Setup expected = new Setup(expectedMap, "A", expectedChosenPlaces, expectedAllPlaces);

        if (expected.equals(result)) {
            assert true;
        } else {
            assert false;
        }

    }

    @Test
    public void shouldRecognizeInvalidPointIdInChosenPointFile() {
        errContent.reset();

        File file1 = new File("src/source/test/data/reader_1.txt");
        File file2 = new File("src/source/test/data/reader_0.txt");
        Setup result = Reader.setUpData(new String[]{file1.getAbsolutePath(), "A", file2.getAbsolutePath()});

        if (errContent.toString().contains("Plik wybranych miejsc: krytyczny błąd odczytu pliku") && result == null) {
            assert true;
        } else {
            System.out.println(errContent.toString());
            assert false;
        }
    }

    @Test
    public void shouldReportNoArguments() {
        outContent.reset();

        Setup result = Reader.setUpData(new String[]{});

        if (outContent.toString().contains("Brak podanych argumentów wejściowych") && result == null) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test
    public void shouldReportMissingFile() {
        errContent.reset();
        File file1 = new File("src/source/test/data/reader_0.txt");
        Setup result = Reader.setUpData(new String[]{file1.getAbsolutePath(), "A"});

        if (result == null && errContent.toString().contains("Plik o podanej ścieżce: " + file1.getAbsolutePath() + " nie istnieje.")) {
            assert true;
        } else {
            assert false;
        }

    }

    @Test
    public void shouldReportMissingStartingPoint() {
        errContent.reset();
        File file1 = new File("src/source/test/data/reader_2.txt");
        Setup result = Reader.setUpData(new String[]{file1.getAbsolutePath()});

        if (result == null && errContent.toString().contains("Argument wejściowy: brak podanego punktu startowego")) {
            assert true;
        } else {
            assert false;
        }

    }

    @Test
    public void shouldRecognizeRepeatedPlaceId() {
        errContent.reset();
        File file1 = new File("src/source/test/data/reader_4.txt");
        Setup result = Reader.setUpData(new String[]{file1.getAbsolutePath(), "ustrzyki_g"});
        if (result != null && errContent.toString().contains("już zostało podane w pliku. Pomijam:")) {
            assert true;
        } else {
            assert false;
        }

    }

    @Test
    public void shouldRecognizeRepeatedVortex() {
        errContent.reset();
        File file1 = new File("src/source/test/data/reader_5.txt");
        Setup result = Reader.setUpData(new String[]{file1.getAbsolutePath(), "ustrzyki_g"});
        if (result != null && errContent.toString().contains("Takie połączenie już istnieje! Pomijam linię.")) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test
    public void shouldIngoreNegativePrice() {
        errContent.reset();
        File file1 = new File("src/source/test/data/reader_6.txt");
        Setup result = Reader.setUpData(new String[]{file1.getAbsolutePath(), "ustrzyki_g"});
        if (result != null && errContent.toString().contains("Koszt trasy nie może być ujemny! Przyjmuję wstęp darmowy.")) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test
    public void shouldIgnoreNonNumeralPrice() {
        errContent.reset();
        File file1 = new File("src/source/test/data/reader_7.txt");
        Setup result = Reader.setUpData(new String[]{file1.getAbsolutePath(), "ustrzyki_g"});
        if (result != null && errContent.toString().contains("Podana kwota nie jest liczbą! Przyjmuję wstęp darmowy.")) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test
    public void shouldRecognizeNegativeTravelTime() {
        errContent.reset();
        File file1 = new File("src/source/test/data/reader_8.txt");
        Setup result = Reader.setUpData(new String[]{file1.getAbsolutePath(), "ustrzyki_g"});
        if (result == null && errContent.toString().contains("Czas podróży między punktami musi być większy od zera!")) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test
    public void shouldRecognizeInvalidStartingPoint() {
        errContent.reset();
        File file1 = new File("src/source/test/data/reader_2.txt");
        Setup result = Reader.setUpData(new String[]{file1.getAbsolutePath(), "ABCD"});
        if (result == null && errContent.toString().contains("Argument wejściowy: punkt startowy")) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test
    public void shouldRecognizeInvalidPlaceIdInConnections() {
        errContent.reset();
        File file1 = new File("src/source/test/data/reader_9.txt");
        Setup result = Reader.setUpData(new String[]{file1.getAbsolutePath(), "ustrzyki_g"});
        if (result == null && errContent.toString().contains("w siatce połączeń nie istnieje na mapie.")) {
            assert true;
        } else {
            assert false;
        }
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}
