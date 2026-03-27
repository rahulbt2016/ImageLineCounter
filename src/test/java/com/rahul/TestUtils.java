package com.rahul;

import java.io.File;

public class TestUtils {

    public static String imagePath(String filename) {
        try {
            return new File(TestUtils.class.getClassLoader().getResource(filename).toURI()).getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("Test resource not found: " + filename, e);
        }
    }
}
