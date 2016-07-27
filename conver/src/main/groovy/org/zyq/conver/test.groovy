package org.zyq.conver

import org.apache.commons.io.FileUtils

/**
 * Created by zouyq on 2016/3/15.
 */
class test {
    static void copydir(def aid) {
        def d = aid;
        if (new File("d:/dirs/${d}").exists()) {
            FileUtils.deleteDirectory(new File("d:/dirs/${d}"));
        }
        FileUtils.copyDirectory(new File("\\\\147.1.6.30\\attwjsf\\${d}"), new File("d:/dirs/${d}"))
    }

    static void copyto30(def id) {
        def d = id;
        File dir30 = new File("\\\\147.1.6.30\\attwjsf\\${d}");
        File bd = new File("d:/dirs/${d}")
        if (bd.exists() && dir30.exists()) {
            FileUtils.deleteDirectory(dir30);
        }
        FileUtils.copyDirectory(new File("d:/dirs/${d}"), dir30)
    }

    public static void main(String[] args) {
        def d = 1609515469;
        copydir(d);
//        copyto30(d);
//        t('ipconfig')
//        t('ping www.baidu.com');
//        t('ping home.iworker.cn')
//        t('ping www.qq.com')
//        t('ping bbs.csdn.net')
//        for (String s : FileUtils.readLines(new File("D:\\JAVA\\workspace\\git\\utils\\src\\main\\resources\\ttt"))) {
//            if (s.length() > 6) {
//                println s.substring(0, 6)
//            }
//        }
    }

    static Thread t(url) {
        new Thread(new Runnable() {
            void run() {
                println new InputStreamReader("cmd /c ${url}".execute().inputStream, "GBK").text
            }
        }).start();
    }

}
