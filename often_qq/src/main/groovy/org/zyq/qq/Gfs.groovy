package org.zyq.qq

import com.jfinal.kit.PathKit
import com.jfinal.kit.PropKit
import com.jfinal.plugin.activerecord.generator.Generator
import com.jfinal.plugin.druid.DruidPlugin

import javax.sql.DataSource

/**
 * Created by Administrator on 2016/5/7.
 */
class Gfs {

    public static DataSource getDataSource() {
        PropKit.use("db.properties");
        DruidPlugin plugin = WebConfig.createDrid();
        plugin.start();
        return plugin.getDataSource();
    }

    public static void main(String[] args) {
        String pa = PathKit.getWebRootPath() + "/..\\src\\main\\groovy\\org\\zyq\\qq\\model\\base"
        Generator gernerator = new Generator(getDataSource(), "org.zyq.qq.model.base", pa, "org.zyq.qq.model", pa + "/..");
        // 添加不需要生成的表名
        gernerator.setChoose_parts('^qq_.*$');
        gernerator.addExcludedTable("adv");
        // 设置是否在 Model 中生成 dao 对象
        gernerator.setGenerateDaoInModel(true);
        // 设置是否生成字典文件
        gernerator.setGenerateDataDictionary(false);
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
        gernerator.setRemovedTableNamePrefixes("qq_");
        // 生成
        gernerator.generate();
    }
}
