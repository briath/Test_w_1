package ru.nsu.fit.shared;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Timur Zolotuhin (tzolotuhin@gmail.com)
 */
public class ImageUtils {
    public static byte[] toByteArray(File file) throws IOException {
        return Files.readAllBytes(Paths.get(file.getPath()));
    }
}
