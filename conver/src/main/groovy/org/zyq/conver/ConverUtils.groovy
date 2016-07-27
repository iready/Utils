package org.zyq.conver

import com.jacob.activeX.ActiveXComponent
import com.jacob.com.Dispatch
import com.jacob.com.Variant
import org.apache.commons.io.FileUtils
import org.apache.log4j.BasicConfigurator
import org.apache.log4j.Logger
import org.zyq.conver.pdf.D2PConst
import org.zyq.core.lang.InitUtils
import org.zyq.core.lang.Str

/**
 * Created by zouyq on 2016/2/26.
 */
class ConverUtils {
    private static Logger logger = Logger.getLogger("Conver");
    private static int count = 0;
    private static final bjf = "==========";
    private static ConverConfig config;

    static void setConfig(ConverConfig config) {
        this.config = config;
    }
    static List<String> doc = ['doc', 'docx', 'txt', 'odt'];
    static {
        InitUtils.insert_assembly("/jacob-1.16-M1-x64.dll", "/jacob-1.16-M1-x86.dll");
    }

    public static String getPrefix(File file) {
        return Str.getPrefix(file);
    }

    public static String getPrefix(String file) {
        return Str.getPrefix(file);
    }

    public static String getSuffix(File file) {
        return Str.getSuffix(file);
    }

    public static String getSuffix(String file) {
        return Str.getSuffix(file);
    }

    static boolean isConverSuccess(File dir, String destName) {
        return dir.list().findAll {
            if (it == destName) {
                return true;
            }
            return false;
        }
    }

    private static boolean check_file(File source_file, File dest_file) {
        if (!source_file.exists()) {
            logger.error "《${source_file.name}》源文件不存在"
            return false;
        }
        if (dest_file.exists()) {
            logger.info "[${dest_file.name}] is existed,now delete [${dest_file.name}] ->${FileUtils.deleteQuietly(dest_file)}"
        }
        if (!dest_file.parentFile.exists()) {
            dest_file.parentFile.mkdirs();
        }
        return true;
    }

    public static boolean office2013_pdf_to_doc(File target_file, File source_file) {
        File dest = new File(target_file, "${getPrefix source_file}.doc");
        if (!check_file(source_file, dest)) {
            return false;
        }
        try {
            ActiveXComponent pdf = new ActiveXComponent("Word.Application");
            pdf.setProperty("Visible", false);
            Dispatch doc = Dispatch.call(pdf.getProperty("Documents").toDispatch(), "Open", source_file.absolutePath, false, true).toDispatch();
            Dispatch.call(doc, "SaveAs", new Variant(target_file.absolutePath));
            Dispatch.call(doc, "Close", false);
            pdf.invoke("Quit", 0);
        } catch (Exception e) {
            logger.error e;
        }
        if (target_file.exists()) {
            return true;
        }
        return false;
    }
    /**
     *
     * @param taget_dir
     * @param source_file
     * @return
     */
    static boolean office_2013_toPDF(File taget_dir, File source_file) {
        long d1 = System.currentTimeMillis();
        File dest = new File(taget_dir, getPrefix(source_file) + '.pdf');
        boolean result = false;
        try {
            if (!check_file(source_file, dest)) {
                return result;
            }
            new D2PConst().convert2PDF(source_file.absolutePath, dest.absolutePath);
            result = isConverSuccess(taget_dir, dest.name);
        } catch (Exception e) {
            logger.error e;
            return result;
        } finally {
            logger.info "${bjf}转换${result ? '成功' : '失败'}《${source_file}》用时 ${(System.currentTimeMillis() - d1) / 1000}s${bjf}"
        }
        return result;
    }

    static boolean run_all_flows_to_swf(File taget_dir, File source_file) {
        long d1 = System.currentTimeMillis();
        boolean result = false;
        try {
            result = cmd_print2swf(taget_dir, source_file);
            if (!result) {
                if (source_file.name.endsWith("x") && config != null) {
                    String oldname = getPrefix(source_file.name);
                    File tempFile = new File("${config.temp_path}/${System.currentTimeMillis()}", "${oldname}.doc");
                    FileUtils.moveFile(source_file, tempFile);
                    result = cmd_print2swf(taget_dir, tempFile);
                    FileUtils.deleteQuietly(tempFile);
                    if (!result) {
                        logger.error "转换期间发生错误，进行错误猜测后仍然失败";
                    }
                } else {
                    result = false;
                    logger.error "转换发生不可预知的错误"
                }
            }
        } catch (Exception e) {
            logger.error(e)
        } finally {
            logger.info "${bjf}转换《${source_file}》${result ? '成功' : '失败'}!用时 ${(System.currentTimeMillis() - d1) / 1000}s${bjf}"
        }
        return result;
    }

    static boolean cmd_print2swf(File dir, File source) {
        String oldName = getPrefix(source);
        File dest = new File(dir, "${oldName}.swf"), tempdir = new File("${config.temp_path}/${System.currentTimeMillis()}");
        tempdir.mkdirs();
        try {
            if (!check_file(dir, dest)) return false;
            String tempFileName = "${System.currentTimeMillis()}";
            File tempSwfFile = new File(tempdir, "${tempFileName}.swf"), tempFile = new File(tempdir, "${tempFileName}.${getSuffix source}");
            FileUtils.copyFile(source, tempFile);
            if (config != null && config.path_printer2swf != null) {
                Process p = "${config.path_printer2swf}/p2fServer.exe ${tempFile.absolutePath} ${tempSwfFile.absolutePath}".execute();
                p.waitFor(); p.destroy();
                if (isConverSuccess(tempdir, tempSwfFile.name)) {
                    FileUtils.moveFile(tempSwfFile, dest);
                    return isConverSuccess(dest.parentFile, dest.name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtils.deleteQuietly(tempdir);
        }
    }

    static boolean jacob_print2swf(File dir, File source) {
/*p2fServer.DefaultBatchProcessingOptions.BeforePrintingTimeout=120000;//改成两分钟
        def run_ = {
            File sourf ->
                ActiveXComponent p2f = new ActiveXComponent("Print2Flash4.Server");
                ActiveXComponent defProfile = new ActiveXComponent(p2f.getProperty("DefaultProfile").toDispatch());
                ActiveXComponent bath = new ActiveXComponent("Print2Flash4.BatchProcessingOptions");
                int t = 1000000000;
                bath.setProperty("ActivityTimeout", t);
                bath.setProperty("AfterPrintingTimeout", t);
                bath.setProperty("BeforePrintingTimeout", t);
                bath.setProperty("PrintingTimeout", t);
                bath.invoke('ApplyChanges');
                int basic = P2FConst.INTROTATE | P2FConst.INTSEARCHBOX | P2FConst.INTSEARCHBUT | P2FConst.INTPRINT | P2FConst.INTNEWWIND | P2FConst.INTZOOMSLIDER | P2FConst.INTPREVPAGE | P2FConst.INTGOTOPAGE | P2FConst.INTNEXTPAGE | P2FConst.INTDRAG;
                if (doc.contains(getSuffix(source_file))) {
                    defProfile.setProperty("InterfaceOptions", P2FConst.INTSELTEXT | basic);
                } else {
                    defProfile.setProperty("InterfaceOptions", basic);
                }
                defProfile.setProperty("ProtectionOptions", P2FConst.PROTDISPRINT | P2FConst.PROTENAPI);
                defProfile.setProperty("DocumentType", P2FConst.FLASH);
                p2f.invoke("ConvertFile", new Variant(source_file.absolutePath), new Variant(dest.absolutePath), new Variant(null), new Variant(9900000), new Variant(null));
                if (isConverSuccess(taget_dir, dest.name)) {
                    return true;
                } else {
                    return false;
                }
        }*/
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        ConverConfig config1 = new ConverConfig("d:/temp");
        config1.path_printer2swf = "D:\\Print2Flash3";
        setConfig(config1);
//        println office2013_pdf_to_doc(new File("d:/aa.doc"), new File('D:\\dirs\\160681616169\\2016-03-09T20160309160945.pdf'));
//        println office_2013_toPDF(new File('D:/dir'), new File("D:\\dirs\\160951038208\\钦州市中级人民法院关于在“开展查处发生在群众身边的‘四风’和腐败问题专项工作”中进行案件抽查回访的工作方案.doc"))
//        println print2flash_toswf(new File('d:/dirs'), new File('D:\\ff.pdf'))
        cmd_print2swf(new File('d:'), new File("d:/test.doc"))
//        println run_all_flows_to_swf(new File('d:/dirs'), new File('D:\\4-地方法院人事信息管理系统升级部署手册.docx'))
    }
}