package org.zyq.core.lang;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zouyq on 2016/3/10.
 */
public class InitUtils {
    private static final String SYSTEM = "C:\\Windows\\System32";

    public static void insert_assembly(String... fileOfClassPath) {
        List<String> re = Arrays.asList(new File(SYSTEM).list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (new File(dir, name).isFile()) return true;
                return false;
            }
        }));
        File f1;
        for (String f : fileOfClassPath) {
            f1 = new File(f);
            if (!re.contains(f1.getName())) {
                try {
                    FileUtils.copyInputStreamToFile(InitUtils.class.getResourceAsStream(f), new File(SYSTEM + "/" + f1.getName()));
                } catch (IOException e) {
                    System.err.println("please up to admin!" + e.getMessage());
                }
            }
        }
    }

    /**
     * 安装组件
     **/
    public static void insert_assembly(final File... file) {
        List<String> re = Arrays.asList(new File(SYSTEM).list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (new File(dir, name).isFile()) return true;
                return false;
            }
        }));
        for (File f1 : file) {
            if (!re.contains(f1.getName())) {
                try {
                    FileUtils.copyFileToDirectory(f1, new File(SYSTEM));
                } catch (IOException e) {
                    System.err.println("please up to admin!" + e.getMessage());
                }
            }
        }
    }
}