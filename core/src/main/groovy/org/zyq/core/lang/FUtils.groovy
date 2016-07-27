package org.zyq.core.lang

import org.apache.commons.io.FileUtils
import org.zyq.core.algorithm.Base64

class FUtils {
    public static final String tempPath = "D:/temps";
    public static final File BASE = new File(tempPath);
    static {
        if (BASE.exists()) BASE.deleteDir();
    }

    static File getTempDir() {
        if (!BASE.exists()) BASE.mkdirs();
        File dir = new File(BASE, UUID.randomUUID().toString());
        dir.mkdirs();
        dir.deleteOnExit();
        return dir;
    }

    static byte[] readFileAndDeleteFile(File file) {
        byte[] bytes = FileUtils.readFileToByteArray(file);
        FileUtils.deleteQuietly(file);
        if (file.parentFile.parentFile.absolutePath == BASE.absolutePath) file.parentFile.delete();
        return bytes;
    }

    static File writeFileByBase64(String base64, String all_name) {
        File file = new File(getTempDir(), all_name.trim());
        FileUtils.writeByteArrayToFile(file, Base64.decodeBuffer(base64));
        if (file.exists()) return file; else return null;
    }

    static String readFileAsBase64(File file, boolean isDelete) {
        String b64 = null;
        if (file.exists()) b64 = Base64.encode(FileUtils.readFileToByteArray(file));
        if (isDelete) file.delete();
        return b64;
    }

    static File format_file_name(File file, boolean isDelete) {
        def fname = "${System.currentTimeMillis()}.${Str.getSuffix(file)}";
        def fp = getTempDir();
        file.renameTo(new File(fp, fname));
        if (isDelete) file.delete();
        return new File(fp, fname);
    }

    public static void main(String[] args) {
        
//        println format_file_name(new File("1.aip"), false)
    }
}
