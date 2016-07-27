package org.zyq.always

import org.zyq.always.entity.ipList

/**
 * Created by zouyq on 2016/4/10.
 */
class Route {
    public List<ipList> get_route_info() {
        Process process = "route print".execute();
        List<String> s = new InputStreamReader(process.inputStream, "gbk").readLines();
        List<ipList> l = new ArrayList<ipList>();
        int ipv4 = 0, count = 0, wy = 3, end_ipv4 = 0;
        for (String ss : s) {
            if (ss == 'IPv4 路由表') {
                ipv4 = count;
            }
            if (end_ipv4 == 0 && ipv4 != 0 && ipv4 + wy < count) {
                if (ss.find("=+") != null) {
                    end_ipv4 = count;
                }
            }
            count++;
        }
        for (String ss : s.subList(ipv4 + wy + 1, end_ipv4)) {
            String[] a = ss.trim().split("\\s+");
            if (a[2].find("\\d") != null) {
                l.add(new ipList(a[0], a[1], a[2], a[3], a[4]));
            }
        }
        return l;
    }

    //清空
    public boolean route_clean() {
        get_route_info().each { ipList i ->
            route_delete(i);
        }
    }

    public void route_add(ipList ip) {

    }

    public void route_delete(ipList ip) {
        "route delete ${ip.targetIp} mask ${ip.zwym}".execute();
    }

    public void route_check() {

    }

    public void input_password() {

    }

    public void run() {
        route_clean();
        println get_route_info();
    }

    public static void main(String[] args) {
        new Route().run();
    }
}
