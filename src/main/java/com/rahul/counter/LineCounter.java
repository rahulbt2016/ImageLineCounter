package com.rahul.counter;

import com.rahul.exception.InvalidImageException;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Counts distinct vertical black lines in a black-and-white JPEG image.
 * Only the top half of the image is scanned, since the spec guarantees
 * every line is continuous across both halves.
 */
public class LineCounter {

    // Pixels whose R/G/B channels differ by more than this are considered colored, not grayscale.
    // JPEG compression of a B&W image always produces grayscale artifacts (R ≈ G ≈ B),
    // so a large channel difference reliably indicates a non-B&W source image.
    private static final int MAX_CHANNEL_DIFF = 30;

    // Pixels with all channels below this are classified as black.
    // 128 is used (rather than 0) to absorb JPEG compression darkening near line edges.
    private static final int BLACK_THRESHOLD = 128;

    /**
     * @param imagePath absolute path to a JPEG image
     * @return number of vertical black lines detected
     * @throws IllegalArgumentException if the path is null/empty or the file is not a .jpg/.jpeg
     * @throws IOException              if the file cannot be found or decoded
     * @throws InvalidImageException    if the image contains colored (non-grayscale) pixels
     */
    public int countVerticalLines(String imagePath) throws IOException, InvalidImageException {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            throw new IllegalArgumentException("Image path must not be null or empty.");
        }

        File file = new File(imagePath);
        if (!file.exists()) {
            throw new IOException("File not found: " + imagePath);
        }
        if (!file.isFile()) {
            throw new IOException("Path does not point to a file: " + imagePath);
        }

        String name = file.getName().toLowerCase();
        if (!name.endsWith(".jpg") && !name.endsWith(".jpeg")) {
            throw new IllegalArgumentException("Unsupported file type. Only .jpg and .jpeg files are accepted.");
        }

        BufferedImage image = ImageIO.read(file);
        if (image == null) {
            throw new IOException("Could not decode image: " + imagePath);
        }

        return countLines(image);
    }

    private int countLines(BufferedImage image) throws InvalidImageException {
        int width      = image.getWidth();
        int halfHeight = image.getHeight() / 2;

        int     lineCount = 0;
        boolean inLine    = false;

        for (int x = 0; x < width; x++) {
            if (isLineColumn(image, x, halfHeight)) {
                if (!inLine) {
                    lineCount++;
                    inLine = true;
                }
            } else {
                inLine = false;
            }
        }

        return lineCount;
    }

    // Returns true if the column contains at least one black pixel.
    // Throws if a colored pixel is encountered, as that indicates an invalid image.
    private boolean isLineColumn(BufferedImage image, int x, int scanHeight)
            throws InvalidImageException {
        for (int y = 0; y < scanHeight; y++) {
            Color pixel = new Color(image.getRGB(x, y));
            validatePixel(pixel, x, y);
            if (isBlack(pixel)) {
                return true;
            }
        }
        return false;
    }

    // A colored pixel means R, G, B channels diverge significantly.
    // B&W images (even JPEG-compressed) only produce grayscale pixels where R ≈ G ≈ B.
    private void validatePixel(Color pixel, int x, int y) throws InvalidImageException {
        int max = Math.max(pixel.getRed(), Math.max(pixel.getGreen(), pixel.getBlue()));
        int min = Math.min(pixel.getRed(), Math.min(pixel.getGreen(), pixel.getBlue()));
        if (max - min > MAX_CHANNEL_DIFF) {
            throw new InvalidImageException(
                "Coloured pixel detected at (" + x + ", " + y + "). " +
                "Image must be a black-and-white MS Paint image.");
        }
    }

    private boolean isBlack(Color pixel) {
        int brightness = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
        return brightness < BLACK_THRESHOLD;
    }
}
