package org.zyq.core.lang;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.zyq.core.algorithm.Base64;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by 邹宇泉 on 2016/9/21.
 */
public class FUtils extends FileUtils {
    public static String tempPath = "D:/temps";
    public static final File BASE = new File(tempPath);
    private static Logger logger = Logger.getLogger(FUtils.class);

    static {
        if (BASE.exists()) {
            try {
                deleteDirectory(BASE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static File getTempDir() {
        if (!BASE.exists()) BASE.mkdirs();
        File dir = new File(BASE, UUID.randomUUID().toString());
        dir.mkdirs();
        dir.deleteOnExit();
        return dir;
    }

    static byte[] readFileAndDeleteFile(File file) throws IOException {
        byte[] bytes = readFileToByteArray(file);
        FileUtils.deleteQuietly(file);
        if (file.getParentFile().getParentFile().getAbsolutePath() == BASE.getAbsolutePath())
            file.getParentFile().delete();
        return bytes;
    }

    static File writeFileByBase64(String base64, String all_name) throws IOException {
        File file = new File(getTempDir(), all_name.trim());
        writeByteArrayToFile(file, Base64.decodeBuffer(base64));
        if (file.exists()) return file;
        else return null;
    }

    static String readFileAsBase64(File file, boolean isDelete) throws IOException {
        String b64 = null;
        if (file.exists()) b64 = Base64.encode(readFileToByteArray(file));
        if (isDelete) file.delete();
        return b64;
    }

    public void setTempPath(String tempPath) {
        FUtils.tempPath = tempPath;
    }

}
