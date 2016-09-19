package tools.excel

import org.apache.commons.io.FileUtils
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.*
import org.joda.time.DateTime
import org.zyq.core.lang.NumberUtils

class POIUtils {
    public static POIUtils poiUtils = new POIUtils();
    /**
     * 插入某一行
     * @param rowNum
     * @param sheet
     * @return
     */
    static HSSFRow insertRow(int rowNum, Sheet sheet) {
        sheet.shiftRows(rowNum, sheet.getLastRowNum(), 1, true, false);
        return sheet.createRow(rowNum);
    }
    /**
     * 批量插入某一行
     * @param row
     * @param start
     * @param style
     * @param ob
     */
    static void batchSetView(Row row, int start, CellStyle style, Object... ob) {
        for (int i = start; i < start + ob.length; i++) {
            Cell hssfCell = row.getCell(i);
            if (hssfCell == null) hssfCell = row.createCell(i);
            if (style != null) hssfCell.setCellStyle(style);
            if (ob[i - start] instanceof Integer || ob[i - start] instanceof BigDecimal || ob[i - start] instanceof Long)
                hssfCell.setCellValue((Integer) ob[i - start]);
            else
                hssfCell.setCellValue("${ob[i - start]}");
        }
    }
    /**
     * 批量插入某一行
     * @param row
     * @param start
     * @param style
     * @param ob
     */
    static void batchSetView(Row row, int start, Object... ob) {
        batchSetView(row, start, null, ob);
    }
    /**
     * 删除某一行
     * @param sheet
     * @param row
     * @return
     */
    static POIUtils deleteRow(Sheet sheet, int row) {
        sheet.removeRow(sheet.getRow(row));
        return poiUtils;
    }
    /**
     * 获取模板
     * @param file
     * @return
     */
    @Deprecated
    static HSSFWorkbook getMB(File file) {
        return new HSSFWorkbook(FileUtils.openInputStream(file));
    }

    static Workbook getMB(File file, Class<? extends Workbook> claz) {
        return claz.newInstance(FileUtils.openInputStream(file));
    }

    /**
     * 算法重算
     * @param cell
     */
    static void recalculate(Cell cell) {
        if (cell.CELL_TYPE_FORMULA == cell.getCellType()) {
            cell.setCellFormula(cell.getCellFormula());
        }
    }
    /**
     * 保存文件
     * @param workbook
     * @param file
     */
    static void saveFile(Workbook workbook, File file) {
        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();
    }

    /**
     * 获取一行数据，绝对成立
     * @param sheet
     * @param index
     * @return
     */
    static Row getRow(Sheet sheet, int index) {
        Row row = sheet.getRow(index);
        if (row == null) row = sheet.createRow(index);
        return row;
    }

    /**
     * 单独读取cell
     * @param cell
     * @return
     */
    static Object readAnCell(Cell cell) {
        Object object = null;
        if (cell == null) return object;
        if (cell.cellType == 0) {
            if (DateUtil.isCellDateFormatted(cell)) object = cell.getDateCellValue();
            else object = cell.numericCellValue;
        } else if (cell.cellType == 1) object = cell.stringCellValue;
        return object;
    }

    /**
     * 强制读取成String
     * @param object
     * @return
     */
    static String readAsString(Object object) {
        String result = null;
        if (NumberUtils.isNumic(object))
            result = "${NumberUtils.toInt(object)}";
        else if (object == null) return null;
        else if (object instanceof Date) return new DateTime(object).toString("yyyy-MM-dd");
        else result = object + "";

        return result;
    }
    /**
     * 读取一行
     * @param row
     * @return
     */
    static List<Object> readRow(Row row) {
        if (row == null) return null;
        List<Object> objects = new ArrayList<Object>();
        int st = row.getFirstCellNum(), end = row.getLastCellNum();
        for (int i = st; i < end + 1; i++) {
            Cell cell = row.getCell(i);
            if (cell != null) objects.add(readAnCell(cell));
            else objects.add(null);
        }
        return objects;
    }
    /**
     * 按照sheet中的某一列搜索row
     * @param sheet
     * @param col
     * @param value
     * @return
     */
    public static List<Row> searchByCol(Sheet sheet, int col, Object value) {
        List<Row> rows = [];
        for (int i = sheet.firstRowNum; i < sheet.lastRowNum + 1; i++) {
            Row row = sheet.getRow(i);
            if (row != null && readAnCell(row.getCell(col)) == value) rows.add(row);
        }
        return rows;
    }

    /**
     * 数据读取成日期，绝对成立
     * @param cell
     * @return
     */
    public static Date toDate(Cell cell) {
        println cell.getCellType()
    }

    public static void main(String[] args) {
        File file1 = new File("C:\\Users\\Administrator\\Downloads\\批量导入导出模板2.xls");
        HSSFWorkbook workbook = getMB(file1);
        HSSFSheet sheet = workbook.getSheetAt(3);
//        println toDate(sheet.getRow(1).getCell(9))
        println readRow(searchByCol(sheet, 0, 2).get(0))
//        println readRow(searchByCol(sheet, 2, 1432)[0]);
//        println readAsString(123.0)
//        println readRow(sheet.getRow(1));
//        HSSFRow row = sheet.createRow(2)
//        batchSetView(row, 11, 1, 2, 3, 4)
//        POIUtils.saveFile(workbook, new File("d:\\a.xls"));
//        new File("C:\\Users\\Administrator\\Downloads").listFiles().each {
//            if (it.name.contains('导出信息')) {
//                it.delete();
//            }
//        }
//        file1.delete();
    }
}
