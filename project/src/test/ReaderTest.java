package test;


import org.junit.Test;
import source.Place;
import source.Reader;

import java.util.ArrayList;
import java.util.HashMap;

public class ReaderTest {

    @Test

    public void shoulDoSomething(){
        Reader.readConfigFile("C:\\Users\\piotr\\OneDrive\\Pulpit\\data\\config.txt", null, new ArrayList<>());
    }

}