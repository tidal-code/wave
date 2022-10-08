package com.tidal.wave.filehandlers;


import com.tidal.wave.exceptions.RuntimeTestException;
import com.tidal.wave.utils.Helper;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class Finder {

    private static Path resourceFolder = Paths.get(Helper.getAbsoluteFromRelativePath(FilePaths.RESOURCE_FOLDER_PATH.getPath()));

    private Finder() {
    }

    public static void setFolder(String folder) {
        resourceFolder = Paths.get(folder);
    }

    public static void setTargetAsBaseFolder() {
        resourceFolder = Paths.get(Helper.getAbsoluteFromRelativePath(FilePaths.TARGET_FOLDER_PATH.getPath()));
    }

    public static void resetToResourceFolder(){
        resourceFolder = Paths.get(Helper.getAbsoluteFromRelativePath(FilePaths.RESOURCE_FOLDER_PATH.getPath()));
    }

    public static synchronized File findFile(String fileName) {
       return findFile(fileName, resourceFolder);
    }

    public static synchronized File findFile(String fileName, Path baseFolderPath) {
        try (Stream<String> stringStream = Files.walk(baseFolderPath).map(Path::toString).filter(f -> f.contains(fileName))) {
            Optional<String> filePath = stringStream.findFirst();
            return new File(filePath.orElseThrow(FileNotFoundException::new));
        } catch (IOException e) {
            e.initCause(new FileNotFoundException(String.format(
                    "File with name '%s' cannot be found from the folder or subfolder of '%s'", fileName, resourceFolder)));
            throw new RuntimeTestException(e.getCause());
        }
    }

    public static Optional<File> findFileIfExists(String fileName) {
        return findFileIfExists(fileName, resourceFolder);
    }

    public static Optional<File> findFileIfExists(String fileName, Path baseFolderPath) {
        try (Stream<String> stringStream = Files.walk(baseFolderPath).map(Path::toString).filter(f -> f.contains(fileName))) {
            Optional<String> filePath = stringStream.findFirst();
            if (filePath.isPresent()) {
                return Optional.of(new File(filePath.get()));
            }
        } catch (Exception ignored) {
            //ignored
        }
        return Optional.empty();
    }

    public static String findFilePath(String fileName) {
        File foundFile = Finder.findFile(fileName);
        return foundFile.getPath();
    }

    public static String getAbsoluteFilePath(String fileName) {
        return FilePaths.getAbsoluteFromRelativePath(findFilePath(fileName));
    }

    public static void openFile(String htmlFileName) {
        File htmlFile = Finder.findFile(htmlFileName);
        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            throw new RuntimeTestException("Failed to open the file");
        }
    }
}
