package tools.win;

import org.junit.Test;

import java.io.*;

/**
 * Created by 邹宇泉 on 2016/7/30.
 */
public class NetUtils {

    public static void main(String[] args) throws IOException {
        new NetUtils().test();
    }

    @Test
    public void test() throws IOException {
        Process proces = Runtime.getRuntime().exec("ping www.baidu.com");
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(proces.getInputStream(), "GBK"));
        String line = null;
        if ((line = br.readLine()) != null) {
            System.out.println(line);
        }

    }
}
