package per.zyq.web

import org.zyq.core.algorithm.URLkit

import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletResponse

/**
 * Created by zouyq on 2016/4/20.
 */
class ServletFile {
    static void renderBytes(byte[] bytes, HttpServletResponse response, String filename) {
        response.setHeader("Content-Disposition", "inline;filename=${URLkit.encode filename}");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Length", String.valueOf(bytes.length));
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
