package tools.reg;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.zyq.core.lang.T;

import java.io.File;
import java.io.IOException;

/**
 * Created by 邹宇泉 on 2016/7/26.
 */
public class test {


    @Test
    public void reg() {
        System.out.println(Integer.valueOf("000007d8", 16));
    }

    @Test
    public void reg2() {
        System.out.println(Integer.toString(-2016, 16));
    }

    @Test
    public void delete() throws IOException {
        long d = System.currentTimeMillis();
        FileUtils.deleteDirectory(new File("d:/冰川网络"));
        T.time_diff_seconds(d);
    }
}