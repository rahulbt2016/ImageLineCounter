ImageLineCounter
================

Counts vertical black lines in a black-and-white JPEG image.

BUILD
-----
  mvn package

USAGE
-----
  java -jar target/ImageLineCounter.jar <absolute-path-to-image>

EXAMPLE
-------
  java -jar target/ImageLineCounter.jar C:\images\img_1.jpg

OUTPUT
------
  A single number representing the count of vertical lines detected.
  Any errors are printed to the console — the application will not crash.

RUNNING FROM INTELLIJ IDEA
--------------------------
  1. Open Run > Edit Configurations
  2. Click + and select Application
  3. Set Main class to com.rahul.App
  4. Set Program arguments to the absolute path of your image
     e.g. C:\images\img_1.jpg
  5. Click Run

REQUIREMENTS
------------
  - Java 8 or higher
  - Image must be a .jpg or .jpeg file
  - Image must be black and white, created with MS Paint
