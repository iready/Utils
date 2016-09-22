package org.zyq.core.lang;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IpUtils {
    public static void main(String[] args)
    {
        List<String> list = new ArrayList<String>();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        list.add("dd");
        list.add("bb");
        list.add("ee");
        list.add("dd");
        list.add("ff");
        String temp = "";
        for (int i = 0; i < list.size() - 1; i++)
        {
            for (int j = i + 1; j < list.size(); j++)
            {
                if (list.get(i).equals(list.get(j)))
                {
                    System.out.println("第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + temp);
                }
            }
        }
    }

    public static String getIP(String start) {
        for (String s : getIpList()) {
            if (s.contains(start)) {
                return s;
            }
        }
        return null;
    }

    public static List<String> getIpList() {
        Enumeration allNetInterfaces = null;
        InetAddress ip = null;
        List<String> list = new ArrayList<>();
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address) {
                        if (!netInterface.isLoopback()) {
                            list.add(ip.getHostAddress());
//                            System.out.format("Name:%s IP:%s %n", netInterface.getDisplayName(), ip.getHostAddress());
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return list;
    }
}
