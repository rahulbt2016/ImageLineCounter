package com.rahul.counter;

import com.rahul.exception.InvalidImageException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.rahul.TestUtils.imagePath;
import static org.junit.Assert.assertEquals;

public class LineCounterTest {

    private LineCounter counter;


    @Before
    public void setUp() {
        counter = new LineCounter();
    }

    // --- Core detection tests ---

    @Test
    public void testImg1ReturnsOneLine() throws IOException, InvalidImageException {
        assertEquals(1, counter.countVerticalLines(imagePath("img_1.jpg")));
    }

    @Test
    public void testImg2ReturnsThreeLines() throws IOException, InvalidImageException {
        assertEquals(3, counter.countVerticalLines(imagePath("img_2.jpg")));
    }

    @Test
    public void testImg3ReturnsOneLine() throws IOException, InvalidImageException {
        assertEquals(1, counter.countVerticalLines(imagePath("img_3.jpg")));
    }

    @Test
    public void testImg4ReturnsSevenLines() throws IOException, InvalidImageException {
        assertEquals(7, counter.countVerticalLines(imagePath("img_4.jpg")));
    }

    // --- Input validation tests ---

    @Test(expected = IllegalArgumentException.class)
    public void testNullPathThrows() throws IOException, InvalidImageException {
        counter.countVerticalLines(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyPathThrows() throws IOException, InvalidImageException {
        counter.countVerticalLines("   ");
    }

    @Test(expected = IOException.class)
    public void testFileNotFoundThrows() throws IOException, InvalidImageException {
        counter.countVerticalLines("C:/nonexistent/image.jpg");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonJpegExtensionThrows() throws IOException, InvalidImageException {
        counter.countVerticalLines(imagePath("png_img.png"));
    }

    @Test(expected = IOException.class)
    public void testDirectoryPathThrows() throws IOException, InvalidImageException {
        counter.countVerticalLines(System.getProperty("java.io.tmpdir"));
    }

    @Test(expected = InvalidImageException.class)
    public void testColoredImageThrows() throws IOException, InvalidImageException {
        counter.countVerticalLines(imagePath("colored_img.jpeg"));
    }

    @Test(expected = IOException.class)
    public void testCorruptImageThrows() throws IOException, InvalidImageException {
        counter.countVerticalLines(imagePath("corrupt_text_file.jpg"));
    }
}
