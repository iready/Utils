package org.zyq.core.lang;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by 邹宇泉 on 2016/8/9.
 */
public class BuildUtils {
    public static void wsdlBuild(String wsdl2java, String base, String wsdlUrl, String file) throws IOException, InterruptedException {
        File dir = new File(base), file1 = new File(dir, file);
        if (file1.exists()) FileUtils.deleteDirectory(file1);
        Process exec = Runtime.getRuntime().exec(String.format("cmd /c %s -p %s -client  -d %s -all %s", wsdl2java, file, base, wsdlUrl));
        exec.waitFor();
        Encode.change(file1, "gbk", "utf-8");
    }
}
