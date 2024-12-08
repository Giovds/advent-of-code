package com.giovds;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// ChatGPT generated :)
public class Summary {

    public static void main(String... args) {
        try {
            // Define the package to scan
            String packageName = "com.giovds"; // Replace with your package name
            String packagePath = packageName.replace('.', '/');
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            // Get all class files in the package
            URL packageURL = classLoader.getResource(packagePath);

            File directory = new File(packageURL.getFile());

            // Collect all classes in the package
            List<Class<?>> classes = new ArrayList<>();
            Files.walk(Paths.get(directory.toURI()))
                    .filter(Files::isRegularFile)
                    .filter(file -> file.toString().endsWith(".class"))
                    .forEach(file -> {
                        String relativePath = directory.toPath().relativize(file).toString();
                        String className = packageName + "." + relativePath.replace(File.separatorChar, '.').replace(".class", "");
                        try {
                            classes.add(Class.forName(className));
                        } catch (ClassNotFoundException e) {
                            System.err.println("Could not load class: " + className);
                        }
                    });

            // Execute main methods in each class, excluding this class
            String thisClassName = Summary.class.getName();
            for (Class<?> clazz : classes) {
                if (clazz.getName().equals(thisClassName)) continue;

                try {
                    Method mainMethod = clazz.getMethod("main", String[].class);
                    if (mainMethod.getReturnType().equals(void.class)) {
                        mainMethod.invoke(null, (Object) new String[]{}); // Pass an empty String array as args
                        System.out.println();
                    }
                } catch (NoSuchMethodException e) {
                    // Ignore classes without a main method
                } catch (Exception e) {
                    System.err.printf("Error executing main method in class: %s%n", clazz.getName());
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
