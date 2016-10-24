package tools.rdp;

import com.sun.jna.platform.win32.Crypt32Util;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by 邹宇泉 on 2016/7/26.
 */
public class RdpUtils {
    String sv = "screen mode id:i:1 %n" +
            "desktopwidth:i:1280 %n" +
            "desktopheight:i:750 %n" +
            "session bpp:i:24 %n" +
            "winposstr:s:2,3,188,8,1062,721 %n" +
            "full address:s:%s%n" +
            "compression:i:1 %n" +
            "keyboardhook:i:2 %n" +
            "audiomode:i:0 %n" +
            "redirectdrives:i:0 %n" +
            "redirectprinters:i:0 %n" +
            "redirectcomports:i:0 %n" +
            "redirectsmartcards:i:0 %n" +
            "displayconnectionbar:i:1 %n" +
            "autoreconnection %n" +
            "enabled:i:1 %n" +
            "username:s:%s%n" +
            "password 51:b:%s%n" +
            "alternate shell:s: %n" +
            "shell working directory:s: %n" +
            "disable full window drag:i:1 %n" +
            "disable menu anims:i:1 %n" +
            "disable themes:i:0 %n" +
            "disable cursor setting:i:0 %n" +
            "bitmapcachepersistenable:i:1";

    @Test
    public void test() throws IOException {
        toRDP("D:/ff.rdp", "147.1.7.19:8234", "administrator", "1234");
    }

    @Test
    public void a() {
        System.out.println("123".compareTo("222"));
    }

    public String createContent(String addr, String username, String password) throws UnsupportedEncodingException {
        return String.format(sv, addr, username, new String(Hex.encodeHex(Crypt32Util.cryptProtectData(password.getBytes("UTF-16LE")))));
    }

    public void toRDP(String path, String addr, String user, String pass) throws IOException {
        FileUtils.writeStringToFile(new File(path), createContent(addr, user, pass));
    }
}