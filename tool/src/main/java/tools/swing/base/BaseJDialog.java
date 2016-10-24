package tools.swing.base;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 邹宇泉 on 2016/7/10.
 */
public class BaseJDialog extends JDialog {

    public BaseJDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        hook_init();
    }

    public void hook_init() {
        layout_hook();
        event_hook();
    }

    public void layout_hook() {

    }

    public void event_hook() {

    }
}
