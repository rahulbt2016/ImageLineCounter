package com.rahul;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.rahul.TestUtils.imagePath;
import static org.junit.Assert.assertEquals;

public class AppTest {

    private PrintStream originalOut;
    private ByteArrayOutputStream outputStream;


    @Before
    public void setUp() {
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testValidImageReturnsLineCount() {
        int exitCode = App.run(new String[]{imagePath("img_1.jpg")});
        assertEquals(0, exitCode);
        assertEquals("1", outputStream.toString().trim());
    }

    @Test
    public void testNoArgsReturnsExitCode1() {
        int exitCode = App.run(new String[]{});
        assertEquals(1, exitCode);
    }

    @Test
    public void testInvalidFileReturnsExitCode2() {
        int exitCode = App.run(new String[]{"C:/nonexistent/image.jpg"});
        assertEquals(2, exitCode);
    }
}
