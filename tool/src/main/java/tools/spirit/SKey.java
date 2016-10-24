package tools.spirit;


import org.zyq.core.lang.Str;

import java.awt.*;
import java.awt.event.KeyEvent;

class SKey {
    public static Robot robot = null;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void sayString(String content) {
//        String old = Str.getSysClipboardText();
        Str.setSysClipboardText(content);
        robot.delay(100);
        combination_key(KeyEvent.VK_CONTROL, KeyEvent.VK_V);
        robot.delay(100);
//        Str.setSysClipboardText(old)
    }

    static void keyPress(int key) {
        robot.keyPress(key);
        robot.keyRelease(key);
    }

    public static void keyPress_noRelease(int key, int count) {
        for (int i = 0; i < count; i++) {
            keyPress(key);
        }
    }

    public static void delay(int time) {
        robot.delay(time);
    }

    public static void combination_key(Integer... keys) {
        for (Integer i : keys) {
            robot.keyPress(i);
        }
        for (Integer i : keys) {
            robot.keyRelease(i);
        }
    }


    public static void main(String[] args) {
//        combination_key(KeyEvent.VK_WINDOWS, KeyEvent.VK_E)
        keyPress_noRelease(KeyEvent.VK_W, 10);
        keyPress_noRelease(KeyEvent.VK_W, 10);
        keyPress_noRelease(KeyEvent.VK_W, 10);
    }
}
