package de.sdrs.servermanager_v2.api.util;

import java.io.IOException;

public class File {

    public static boolean delete(java.io.File directoryToBeDeleted) {
        java.io.File[] allContents =directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (java.io.File file : allContents) {
                delete(file);
            }
        }
        return directoryToBeDeleted.delete();

    }

    public static void create(java.io.File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
