package org.zyq.core.lang;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Encode {
    public static void change(File source, File target, String source_encode, String target_encode) {
        try {
            FileUtils.writeStringToFile(target, FileUtils.readFileToString(source, source_encode), target_encode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void change(File target_dir, String source_encode, String target_encode) {
        List<File> files = (List<File>) FileUtils.listFiles(target_dir, new FileFileFilter() {
            @Override
            public boolean accept(File file) {
                return super.accept(file);
            }
        }, null);
        try {
            for (File file : files) {
                FileUtils.writeStringToFile(file, FileUtils.readFileToString(file, source_encode), target_encode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        change(new File("D:\\JAVA\\workspace\\mime\\client\\src\\main\\groovy\\fyclient"), "gbk", "utf-8");
    }
}