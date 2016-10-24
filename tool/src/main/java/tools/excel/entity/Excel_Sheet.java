package tools.excel.entity;

/**
 * Created by zouyq on 2016/4/19.
 */
public class Excel_Sheet {
    private String sheet_name;
    private String sheet_content;

    public Excel_Sheet() {
    }

    public Excel_Sheet(String sheet_name, String sheet_content) {
        this.sheet_name = sheet_name;
        this.sheet_content = sheet_content;
    }

    public String getSheet_name() {
        return sheet_name;
    }

    public void setSheet_name(String sheet_name) {
        this.sheet_name = sheet_name;
    }

    public String getSheet_content() {
        return sheet_content;
    }

    public void setSheet_content(String sheet_content) {
        this.sheet_content = sheet_content;
    }

}
