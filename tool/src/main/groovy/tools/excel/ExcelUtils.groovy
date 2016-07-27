package tools.excel

import com.jacob.activeX.ActiveXComponent
import com.jacob.com.ComThread
import com.jacob.com.Dispatch
import com.jacob.com.Variant
import jxl.Workbook
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
import org.apache.commons.io.FileUtils
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.zyq.core.lang.InitUtils
import org.zyq.core.lang.Str
import per.zouyq.excel.entity.Excel_Sheet
import tools.excel.entity.Excel_Sheet

class ExcelUtils {
    static {
        InitUtils.insert_assembly("/jacob-1.16-M1-x64.dll", "/jacob-1.16-M1-x86.dll");
    }

    static byte[] toExcel(Excel_Sheet[] sheets) {

    }

    static byte[] JXLtoExcel(File exl, Excel_Sheet[] sheets) {
        WritableWorkbook workbook = Workbook.createWorkbook(exl);
        for (int i = 0; i < sheets.length; i++) {
            WritableSheet sheet = workbook.createSheet(sheets[i].sheet_name, i);
        }
        workbook.write();
        workbook.close();
    }

    static byte[] JACOBtoExcel(Excel_Sheet[] sheets) {
        ComThread.InitSTA();
        ActiveXComponent app = new ActiveXComponent("Excel.Application");
        app.setProperty("Visible", false);
        File file = null;
        try {
            Dispatch excels = app.getProperty("Workbooks").toDispatch();
            Dispatch excel = Dispatch.call(excels, "Add").toDispatch();
            for (Excel_Sheet sheet : sheets) {
                Str.setSysClipboardText(sheet.sheet_content);
                Dispatch s = Dispatch.call(excel, "Worksheets", 1).toDispatch();
                Dispatch.call(s, "Paste");
                Dispatch.put(s, "name", sheet.sheet_name);
                File dir = new File("D:/temp");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                file = new File(dir, "${System.currentTimeMillis()}.xlsx");
                Object[] objects = new Object[1];
                objects[0] = file.absolutePath;
//                objects[1] = new Variant(44);
                Dispatch.invoke(excel, 'SaveAs', Dispatch.Method, objects, new int[1]);
                Dispatch.call(excel, 'Close', new Variant(false));
            }
            byte[] bytes = FileUtils.readFileToByteArray(file);
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            FileUtils.deleteQuietly(file);
            app.invoke("Quit");
            ComThread.Release();
        }
    }

    private static boolean t(String a) {
        a = a + ",".replace("\\s", "").trim()
        String b = a.replace(a.substring(0, a.indexOf(",") + 1), "");
        return "".equals(b);
    }

    public static void main(String[] args) {
//        Excel_Sheet[] sheets = new Excel_Sheet[1];
//        sheets[0] = new Excel_Sheet("我的", FileUtils.readFileToString(new File("D:/new 3.txt")));
//        println JACOBtoExcel(sheets).length
        HSSFWorkbook workbook = POIUtils.getMB(new File("D:\\登记表模板.xls"));
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom((short) 1);
        style.setBorderRight((short) 1);
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow row1 = sheet.getRow(1);
        for (int i = 0; i < 10; i++) {
            HSSFRow row = POIUtils.insertRow(i, sheet);
            POIUtils.batchSetView(row, 1, style, i, 2, 3, 4, 5, 6, 7, 8, 9);
        }
        POIUtils.saveFile(workbook, new File("d:/work.xls"))
    }
}
