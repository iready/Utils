package org.zyq.conver.pdf;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComFailException;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.zyq.conver.ConverUtils;

import java.io.File;

/**
 * Created by zouyq on 2016/2/26.
 */
public class D2PConst {
    private static final int wdFormatPDF = 17;
    private static final int xlTypePDF = 0;
    private static final int ppSaveAsPDF = 32;
    private static final int msoTrue = -1;
    private static final int msofalse = 0;
    private static Logger logger = Logger.getLogger(D2PConst.class);

    public static String getFileSufix(String fileName) {
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(splitIndex + 1);
    }

    public boolean convert2PDF(String inputFile, String pdfFile) {
        String suffix = getFileSufix(inputFile);
        if (ConverUtils.getDoc().contains(suffix)) {
            return word2PDF(inputFile, pdfFile);
        } else if (suffix.equals("ppt") || suffix.equals("pptx")) {
            return ppt2PDF(inputFile, pdfFile);
        } else if (suffix.equals("xls") || suffix.equals("xlsx")) {
            return excel2PDF(inputFile, pdfFile);
        } else {
            logger.error("not support[" + inputFile + "]");
            return false;
        }
    }

    public boolean word2PDF(String inputFile, String pdfFile) {
        File destFile = null;
        try {
            ComThread.InitSTA();
            ActiveXComponent wordApp = new ActiveXComponent("Word.Application");
            wordApp.setProperty("Visible", false);
            Dispatch docs = wordApp.getProperty("Documents").toDispatch();
            Dispatch doc = null;
            try {
                doc = Dispatch.call(docs,
                        "Open",
                        inputFile,
                        false,
                        true
                ).toDispatch();
            } catch (ComFailException e) {
                logger.error("is cannot open,now try to down level!");
                destFile = new File("d:/myfile/" + ConverUtils.getPrefix(inputFile) + ".doc");
                FileUtils.copyFile(new File(inputFile), destFile);
                doc = Dispatch.call(docs,
                        "Open",
                        destFile.getAbsolutePath(),
                        false,
                        true
                ).toDispatch();
            }
            if (doc == null) {
                logger.error("cannot open " + inputFile + ",and down level failed!");
                return false;
            }
            Dispatch.call(doc,
                    "ExportAsFixedFormat",
                    pdfFile,
                    wdFormatPDF
            );
            Dispatch.call(doc, "Close", false);
            wordApp.invoke("Quit", 0);
            return true;
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (destFile != null) {
                logger.info("deleted " + FileUtils.deleteQuietly(destFile));
            }
            ComThread.Release();
        }
        return false;
    }

    public boolean excel2PDF(String inputFile, String pdfFile) {
        try {
            ActiveXComponent app = new ActiveXComponent("Excel.Application");
            app.setProperty("Visible", false);
            Dispatch excels = app.getProperty("Workbooks").toDispatch();
            Dispatch excel = Dispatch.call(excels,
                    "Open",
                    inputFile,
                    false,
                    true
            ).toDispatch();
            Dispatch.call(excel,
                    "ExportAsFixedFormat",
                    xlTypePDF,
                    pdfFile
            );
            Dispatch.call(excel, "Close", false);
            app.invoke("Quit");
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean ppt2PDF(String inputFile, String pdfFile) {
        try {
            ActiveXComponent app = new ActiveXComponent("PowerPoint.Application");
            //app.setProperty("Visible", msofalse);
            Dispatch ppts = app.getProperty("Presentations").toDispatch();
            Dispatch ppt = Dispatch.call(ppts,
                    "Open",
                    inputFile,
                    true,
                    true,
                    false
            ).toDispatch();
            Dispatch.call(ppt,
                    "SaveAs",
                    pdfFile,
                    ppSaveAsPDF
            );
            Dispatch.call(ppt, "Close");
            app.invoke("Quit");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
