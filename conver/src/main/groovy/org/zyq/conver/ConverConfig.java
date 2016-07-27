package org.zyq.conver;

import java.io.File;

/**
 * Created by zouyq on 2016/4/5.
 */
public class ConverConfig {
    private String path_swftools;
    private String path_printer2swf;
    private String temp_path;

    public ConverConfig(String temp_path) {
        this.temp_path = temp_path;
        File d = new File(temp_path);
        if (!d.exists() && !d.mkdirs()) {
            throw new RuntimeException("创建失败");
        }
        d.deleteOnExit();
    }

    public String getPath_swftools() {
        return path_swftools;
    }

    public ConverConfig setPath_swftools(String path_swftools) {
        this.path_swftools = path_swftools;
        return this;
    }

    public String getPath_printer2swf() {
        return path_printer2swf;
    }

    public ConverConfig setPath_printer2swf(String path_printer2swf) {
        this.path_printer2swf = path_printer2swf;
        return this;
    }

    public String getTemp_path() {
        return temp_path;
    }

    public ConverConfig setTemp_path(String temp_path) {
        this.temp_path = temp_path;
        return this;
    }
}
