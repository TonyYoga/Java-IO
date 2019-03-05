package com.telran.data.entity.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;

public class Utils {
    public static boolean saveAll(Path path, List<?> list) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (int i = 0; i < list.size(); i++) {
                bw.write(list.get(i).toString());
                if (i != list.size() - 1) {
                    bw.newLine();
                }
            }
        }
        return true;
    }

    public static boolean save(Path path, String str) {
        Objects.requireNonNull(str);
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            if (Files.size(path) > 0) {
                bw.newLine();
            }
            bw.write(str);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
