package org.zyq.always.entity;

/**
 * Created by zouyq on 2016/4/10.
 */
public class ipList {
    private String targetIp;//网络目标
    private String zwym;//网络掩码
    private String wg;//网关
    private String jiekou;//接口
    private String yuedian;//跃点数

    public ipList(String targetIp, String zwym, String wg, String jiekou, String yuedian) {
        this.targetIp = targetIp;
        this.zwym = zwym;
        this.wg = wg;
        this.jiekou = jiekou;
        this.yuedian = yuedian;
    }

    public ipList() {

    }

    public String getTargetIp() {
        return targetIp;
    }

    public void setTargetIp(String targetIp) {
        this.targetIp = targetIp;
    }

    public String getZwym() {
        return zwym;
    }

    public void setZwym(String zwym) {
        this.zwym = zwym;
    }

    public String getWg() {
        return wg;
    }

    public void setWg(String wg) {
        this.wg = wg;
    }

    public String getJiekou() {
        return jiekou;
    }

    public void setJiekou(String jiekou) {
        this.jiekou = jiekou;
    }

    public String getYuedian() {
        return yuedian;
    }

    public void setYuedian(String yuedian) {
        this.yuedian = yuedian;
    }

    @Override
    public String toString() {
        return "ipList{" +
                "targetIp='" + targetIp + '\'' +
                ", zwym='" + zwym + '\'' +
                ", wg='" + wg + '\'' +
                ", jiekou='" + jiekou + '\'' +
                ", yuedian='" + yuedian + '\'' +
                '}';
    }
}
