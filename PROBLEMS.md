# Challenges & Solutions

## 1. JPEG Compression Artefacts

The most significant challenge was handling JPEG compression. Although the source images are black and white MS Paint images, JPEG is a lossy format — pixels near the edges of black lines are not pure black or pure white after compression.

Several approaches were explored before arriving at the final solution:

**Approach 1 — Dark pixel ratio:**
The first approach scanned every pixel in a column and calculated the fraction of dark pixels. A column was classified as a line if the ratio exceeded a threshold. This was robust against JPEG noise but required visiting every pixel in the column even after a line had already been confirmed.

**Approach 2 — Exact black match:**
To reduce unnecessary iterations, a simpler approach was tried: return true as soon as a pixel with exact RGB value (0, 0, 0) was found. This was fast but failed on the test images because JPEG compression shifts pure black pixels slightly — a pixel that was (0, 0, 0) in the original image could become (4, 4, 4) after compression since JPEG compresses grayscale values uniformly across all channels, causing lines to be missed entirely.

**Final approach — Brightness threshold with grayscale validation:**
The final solution combines early exit with JPEG tolerance. A pixel is considered black if all channels are below 128 (not exact 0), which absorbs the uniform channel shift introduced by compression. Additionally, image validity is checked by ensuring no pixel has a large divergence between its channels (R, G, B differing by more than 30). Since JPEG compression of a B&W image always shifts channels uniformly (R ≈ G ≈ B), a large channel difference reliably indicates a coloured source image.

## 2. Windows Path Encoding in Tests

When loading test images via `getClass().getClassLoader().getResource()`, the returned URL path on Windows contained URL-encoded characters — spaces in the folder name were encoded as `%20`, producing a path like `/C:/Users/Rahul/Desktop/Toyota%20Coding%20Assignment/...` which `new File()` could not resolve. This was fixed by converting the URL to a URI first (`getResource().toURI()`), which properly decodes the path into a valid Windows file path.
