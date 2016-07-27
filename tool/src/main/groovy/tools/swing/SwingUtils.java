package tools.swing;


import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

/**
 * Created by Yuquan Zou on 2016/1/5.
 */
public class SwingUtils {
    public static SwingUtils s = new SwingUtils();

    private static Logger logger = Logger.getLogger(SwingUtils.class);

    private static JFrame jFrame;

    public static JFrame getjFrame() {
        return jFrame;
    }

    public static void setjFrame(JFrame jframe) {
        jFrame = jframe;
    }

    public static void replaceContent(JComponent jComponent) {
        if (jFrame != null) {
            jFrame.setContentPane(jComponent);
            jFrame.pack();
        } else {
            logger.error("please set main jframe!");
        }
    }


    public static SwingUtils window_init_end(JFrame frame, JComponent panel) {
        frame.setContentPane(panel);
        window_centered(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return s;
    }

    public static SwingUtils window_init_end(JFrame frame, JComponent panel, Dimension dimension) {
        panel.setPreferredSize(dimension);
        panel.setMinimumSize(dimension);
        return window_init_end(frame, panel);
    }

    public static SwingUtils setContent(JComponent jComponent) {
        jFrame.setContentPane(jComponent);
        jFrame.pack();
        return s;
    }

    public static SwingUtils setContent(JFrame frame, JComponent jComponent) {
        frame.setContentPane(jComponent);
        frame.pack();
        return s;
    }

    public static SwingUtils setFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys();
             keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
        return s;
    }

    public static SwingUtils window_centered(JFrame frame) {
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);
        return s;
    }


    /**
     * 初始化完弹窗后的位置定位及显示
     *
     * @param dialog
     * @param cp
     */
    public static void dialog_init(Dialog dialog, Component cp) {
        dialog.pack();
        int x = 0, y = 0;
        Point p = cp.getLocation();
        if (cp.getWidth() > dialog.getWidth()) x = (int) p.getX() + (cp.getWidth() - dialog.getWidth()) / 2;
        if (cp.getHeight() > dialog.getHeight()) y = (int) p.getY() + (cp.getHeight() - dialog.getHeight()) / 2;
        else {
            y = (int) p.getY() - (dialog.getHeight() - cp.getHeight()) / 2;
        }
        dialog.setLocation(x, y);
        dialog.setResizable(false);
        dialog.setVisible(true);

    }

}
