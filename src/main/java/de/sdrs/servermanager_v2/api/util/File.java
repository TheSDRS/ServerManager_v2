package de.sdrs.servermanager_v2.api.util;

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
}
