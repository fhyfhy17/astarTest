package astar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<String> getFileNames(String filePath, String filter) {
        File root = new File(filePath);
        File[] files = root.listFiles(pathname -> {
            String name = pathname.getName();
            return name.endsWith(filter);
        });
        List<String> fileList = new ArrayList<>();
        if (files == null) {
            return fileList;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                getFiles(file.getAbsolutePath(), filter);
            } else {
                fileList.add(file.getAbsolutePath());
            }
        }
        return fileList;
    }

    public static List<File> getFiles(String filePath, String filter) {
        File root = new File(filePath);
        File[] files = root.listFiles(pathname -> {
            String name = pathname.getName();
            return name.endsWith(filter);
        });
        List<File> fileList = new ArrayList<>();
        if (files == null) {
            return fileList;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                getFiles(file.getAbsolutePath(), filter);
            } else {
                fileList.add(file);
            }
        }
        return fileList;
    }
}
