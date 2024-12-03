package com.giovds;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileUtil {
    public static List<String> readFile(final Class<?> resourceClass, final String fileName) throws IOException, URISyntaxException {
        final URL resource = resourceClass.getResource("/%s".formatted(fileName));
        if (fileExists(resource)) {
            throw new IOException("Resource not found: %s".formatted(fileName));
        }
        return Files.readAllLines(Path.of(resource.toURI()));
    }

    private static boolean fileExists(URL resource) throws URISyntaxException {
        return resource == null || Files.notExists(Path.of(resource.toURI()));
    }
}