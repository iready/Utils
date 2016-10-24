package tools.swing.base;

import javax.swing.*;

/**
 * Created by 邹宇泉 on 2016/7/7.
 */
public class BaseJPanel extends JPanel {

    public BaseJPanel() {
        super();
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
