package org.zyq.swing;

import javax.swing.*;
import javax.swing.table.TableColumn;

/**
 * Created by Administrator on 2016/6/5.
 */
public class TableUtils {
    /**
     * 隐藏列
     *
     * @param jTable
     * @param col
     */
    public static void hideColumn(JTable jTable, int col) {
        TableColumn tc = jTable.getColumnModel().getColumn(col);
        tc.setMaxWidth(0);
        tc.setPreferredWidth(0);
        tc.setWidth(0);
        tc.setMinWidth(0);
        jTable.getTableHeader().getColumnModel().getColumn(col).setMaxWidth(0);
        jTable.getTableHeader().getColumnModel().getColumn(col).setMinWidth(0);
    }

}
