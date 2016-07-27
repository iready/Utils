package org.zyq.swing.console;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class PseudoPrintStream extends PrintStream {
    public PseudoPrintStream() {
        super(new OutputStream() {//匿名OutputStream
            public void write(int b) throws IOException {
                //不用实现，只是为了比父类增加一个无参构造函数
            }
        });
    }
}